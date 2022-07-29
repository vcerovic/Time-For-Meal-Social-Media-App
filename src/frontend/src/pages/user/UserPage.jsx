import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import jwt_decode from "jwt-decode";
import { useCookies } from "react-cookie";


const UserPage = () => {
  const [user, setUser] = useState({});
  const [userImage, setUserImage] = useState();
  const [cookies, setCookie] = useCookies();
  let params = useParams();
  let currentUsername = "";
  if (cookies.JWT != null) {
    currentUsername = jwt_decode(cookies.JWT);
  }

  const getUser = async () => {
    try {
      const response = await fetch(`${process.env.REACT_APP_API_URL}/api/v1/users/${params.userId}`);
      const data = await response.json();

      if (!response.ok) {
        throw new Error(data.message);
      }

      setUser(data);
    } catch (err) {
      alert(err.message);
    }
  }

  const getUserImage = async () => {
    try {
      const response =
        await fetch(`${process.env.REACT_APP_API_URL}/api/v1/users/${params.userId}/image`);
      const imageBlob = await response.blob();
      const imageObjectURL = await URL.createObjectURL(imageBlob);

      setUserImage(imageObjectURL);
    } catch (err) {
      alert(err);
    }
  }

  useEffect(() => {
    getUser();
    getUserImage();
  }, []);

  return (
    <div>
      <img src={userImage} alt={user.username} />
      <h1>{user.username}</h1>
      <p>{user.email}</p>
    </div>
  )
}

export default UserPage