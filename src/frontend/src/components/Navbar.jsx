import React, { useEffect, useState } from 'react'
import { Link, useLocation } from 'react-router-dom';
import { useCookies } from 'react-cookie';
import { validateUserToken } from '../api/AuthApi';
import { getUserByUsername, getUserImage } from '../api/UserApi';

import jwt_decode from "jwt-decode";
import logo from '../assets/images/time2meal_logo.png'

const Navbar = () => {
    const [cookies, setCookie] = useCookies(['jwt']);
    const [hasLoaded, setHasLoaded] = useState(false);
    const [user, setUser] = useState();
    const [userImage, setUserImage] = useState();
    const location = useLocation();

    useEffect(() => {
        if (cookies.JWT != null) {
            validateUserToken(cookies.JWT)
                .then(isValid => {
                    if (isValid) {
                        let decoded = jwt_decode(cookies.JWT);
                        getUserByUsername(decoded.sub)
                            .then(data => {
                                setUser(data)
                                getUserImage(data.id)
                                    .then(image => setUserImage(image))
                            })

                    }
                })
                .finally(setHasLoaded(true));
        }

    }, []);


    if (location.pathname === "/") {
        return null
    } else {
        if (hasLoaded) {
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
        } else {
            return <p>Loading...</p>
        }
    }
}

export default Navbar