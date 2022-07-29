import React, { useEffect, useState } from 'react'
import logo from '../assets/images/time2meal_logo.png'
import { Link, useLocation } from 'react-router-dom';
import { useCookies } from 'react-cookie';
import jwt_decode from "jwt-decode";


const Navbar = () => {
    const [cookies, setCookie] = useCookies(['jwt']);
    const [user, setUser] = useState();
    const [userImage, setUserImage] = useState();
    const location = useLocation();
   


    const getUserByUsername = async (currentUsername) => {
        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/api/v1/users?username=${currentUsername}`);
            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message);
            }

            setUser(data);
            getUserImage(data);
        } catch (err) {
            alert(err.message);
        }
    }

    const getUserImage = async (user) => {
        try {
            const response =
                await fetch(`${process.env.REACT_APP_API_URL}/api/v1/users/${user.id}/image`);
            const imageBlob = await response.blob();
            const imageObjectURL = await URL.createObjectURL(imageBlob);

            setUserImage(imageObjectURL);
        } catch (err) {
            alert(err);
        }
    }

    useEffect(() => {
        let currentUsername = "";
        if (cookies.JWT != null) {
            let decoded = jwt_decode(cookies.JWT);
            currentUsername = decoded.sub;

            if (currentUsername !== "") {
                getUserByUsername(currentUsername);
            }
        }
    }, []);


    if (location.pathname === "/") {
        return null
    } else {
        if (!user) {
            return (
                <div className='navbar'>
                    <div>
                        <Link to={'/'}><img className='logo' src={logo} alt="Time for meal logo" /></Link>
                        <Link to={'/'}><h1>Time for meal</h1></Link>
                    </div>
                    <ul>
                        <li><Link to={'/register'}>Register</Link></li>
                        <li><Link to={'/login'}>Login</Link></li>
                    </ul>
                </div>
            )
        } else {
            return (
                <div className='navbar'>
                    <div>
                        <Link to={'/'}><img className='logo' src={logo} alt="Time for meal logo" /></Link>
                        <Link to={'/'}><h1>Time for meal</h1></Link>
                    </div>
                    <ul>
                        <li><Link to={`users/${user.id}`}>{user.username}</Link></li>
                        <li><Link to={`users/${user.id}`}><img src={userImage} alt={user.username} /></Link></li>
                    </ul>
                </div>
            )
        }
    }
}

export default Navbar