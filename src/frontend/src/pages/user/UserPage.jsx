import React, { useState, useEffect } from 'react'
import { Link, useParams, useNavigate } from 'react-router-dom'
import jwt_decode from "jwt-decode";
import { useCookies } from "react-cookie";
import { getUserById, getUserImage, getUserByUsername, getUserRecipes, deleteUser } from '../../api/UserApi';
import { validateUser } from '../../api/AuthApi';
import RecipeCard from '../../components/RecipeCard';
import Swal from "sweetalert2";


const UserPage = () => {
  const [user, setUser] = useState({});
  const [currentUser, setCurrentUser] = useState({});
  const [userRecipes, setUserRecipes] = useState([]);
  const [userImage, setUserImage] = useState();
  const [cookies, setCookie] = useCookies();
  const [hasLoaded, setHasLoaded] = useState(false);

  let params = useParams();
  const navigate = useNavigate();

  const handleDeleteUser = () => {
    Swal.fire({
      title: 'Are you sure you want to delete your account?',
      showCancelButton: true,
      confirmButtonText: 'Delete',
      confirmButtonColor: '#d61717',
    }).then((result) => {
      if (result.isConfirmed) {
        deleteUser(params.userId, cookies)
          .then(() => navigate('/recipes'))
      } else {
        Swal.fire('Okay!', '', 'info')
      }
    })
  }

  useEffect(() => {
    getUserById(params.userId)
      .then(data => {
        setUser(data)
        getUserImage(params.userId)
          .then(image => setUserImage(image))
          .then(() => getUserRecipes(params.userId).then(data => setUserRecipes(data)))

        validateUser(cookies)
          .then(isValid => {
            if (isValid) {
              let decoded = jwt_decode(cookies.JWT);
              getUserByUsername(decoded.sub)
                .then(data => setCurrentUser(data))
            }
          })
          .finally(setHasLoaded(true));
      })


  }, []);

  if (!hasLoaded) {
    return (
      <div id="preloader">
        <div id="loader"></div>
      </div>
    )
  } else {

    return (
      <div id='userPage'>
        <div className='userCard'>
          <img src={userImage} alt={user.username} />
          <div className='info'>
            <h1>{user.username}</h1>
          </div>
          {currentUser.id === user.id ?
            <div className='actions'>
              <Link className='editBtn' to={`/users/${params.userId}/edit`}>Edit</Link>
              <button onClick={handleDeleteUser} className='deleteBtn'>Delete</button>
            </div> : null}

        </div>
        <div className='recipes'>
          <h1>{user.username}'s recipes</h1>
          <div className='content'>
            {userRecipes != null ? userRecipes.map(recipe =>
              <RecipeCard key={recipe.id} recipe={recipe} />) : <div>No published recipes</div>}
          </div>
        </div>
      </div>
    )
  }
}

export default UserPage