import React, { useEffect, useState } from 'react'
import { useCookies } from 'react-cookie';
import { Link, useNavigate } from 'react-router-dom';
import { validateUser, loginUser } from '../../api/AuthApi';

const LoginPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [cookies, setCookie] = useCookies();
    const [isLogged, setIsLogged] = useState(false);
    const [hasLoaded, setHasLoaded] = useState(false);
    const navigate = useNavigate();

    function handleSubmit(event) {
        event.preventDefault();
        loginUser(username, password)
            .then(data => {
                setCookie('JWT', 'Bearer ' + data.jwtToken,
                    { path: '/', maxAge: 259200, sameSite: 'none', secure: 'None' })
            })
            .then(() => navigate('/recipes'));
    }

    useEffect(() => {
        validateUser(cookies)
            .then(isValid => setIsLogged(isValid))
            .finally(setHasLoaded(true));
    }, []);

    if (!hasLoaded) return(
        <div id="preloader">
            <div id="loader"></div>
        </div>
    )
    else {
        if (isLogged) {
            return <p>You are already logged in</p>
        } else {
            return (
                <div id='formPage'>
                    <div className='form-container'>
                        <h1 className='title'>Login</h1>
                        <form onSubmit={handleSubmit}>
                            <div className='field'>
                                <input
                                    id="username"
                                    type="text"
                                    value={username}
                                    placeholder=" "
                                    onChange={(e) => setUsername(e.target.value)}
                                />
                                <label htmlFor="username">Username</label>
                            </div>
                            <div className='field'>
                                <input
                                    id="password"
                                    type="password"
                                    value={password}
                                    placeholder=" "
                                    onChange={(e) => setPassword(e.target.value)}
                                />
                                <label htmlFor="password">Password</label>
                            </div>
                            <div className='options'>
                                <Link to={'/resetPassword'}>Forgot Password?</Link>
                            </div>
                            <button className='linkBtn' type="submit">Submit</button>
                            <div className='options'>
                                Not a member? <Link to="/register">Register</Link>
                            </div>
                        </form>
                    </div>
                </div>

            );
        }
    }
}

export default LoginPage