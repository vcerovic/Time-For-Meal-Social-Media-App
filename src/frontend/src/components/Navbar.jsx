import React, { useEffect, useState } from 'react'
import { Link, useLocation, useNavigate, } from 'react-router-dom';
import { useCookies } from 'react-cookie';
import { validateUser } from '../api/AuthApi';
import { getUserByUsername, getUserImage } from '../api/UserApi';
import jwt_decode from "jwt-decode";
import logo from '../assets/images/time2meal_logo.png'

const Navbar = () => {
    const [cookies, setCookie, removeCookie] = useCookies();
    const [hasLoaded, setHasLoaded] = useState(false);
    const [user, setUser] = useState();
    const [userImage, setUserImage] = useState();
    const location = useLocation();
    const navigate = useNavigate();

    const handleLogout = () => {
        removeCookie('JWT', { path: '/', sameSite: 'none', secure: 'None' });
        navigate('/login');
    }

    useEffect(() => {

        validateUser(cookies)
            .then(isValid => {
                if (isValid) {
                    let decoded = jwt_decode(cookies.JWT);
                    getUserByUsername(decoded.sub)
                        .then(data => {
                            setUser(data)
                            getUserImage(data.id)
                                .then(image => setUserImage(image))
                        })
                } else {
                    setUser(null)
                    setUserImage(null);
                }
            })
            .finally(setHasLoaded(true));
    }, [location]);


    if (location.pathname === "/") {
        return null
    } else {
        if (hasLoaded) {
            if (!user) {
                return (
                    <div className='navbar'>
                        <div>
                            <Link to={'/recipes'}><img className='logo' src={logo} alt="Time for meal logo" /></Link>
                            <Link to={'/recipes'}><h1>Time for meal</h1></Link>
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
                            <Link to={'/recipes'}><img className='logo' src={logo} alt="Time for meal logo" /></Link>
                            <Link to={'/recipes'}><h1>Time for meal</h1></Link>
                        </div>
                        <ul>
                            <li><Link className='recipeBtn'  to={'/recipes/new'}>Post a recipe</Link></li>
                            <li><button onClick={() => handleLogout()}>Logout</button></li>
                            <li><Link to={`users/${user.id}`}>{user.username}</Link></li>
                            <li><Link to={`users/${user.id}`}><img src={userImage} alt={user.username} /></Link></li>
                        </ul>
                    </div>
                )
            }
        } else {
            return <p>Loading...</p>
        }
    }
}

export default Navbar