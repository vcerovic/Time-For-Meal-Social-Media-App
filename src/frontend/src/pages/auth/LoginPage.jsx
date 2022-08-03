import React, { useEffect, useRef, useState } from 'react'
import { useCookies } from 'react-cookie';
import { Link, useNavigate } from 'react-router-dom';
import { validateUser, loginUser, sendResetPasswordRequest } from '../../api/AuthApi';
import { validateUserLogin } from '../../utils/ValidationUtils';
import Swal from "sweetalert2";

const LoginPage = () => {
    const [cookies, setCookie] = useCookies();
    const [isLogged, setIsLogged] = useState(false);
    const [hasLoaded, setHasLoaded] = useState(false);
    const navigate = useNavigate();

    const usernameRef = useRef();
    const passwordRef = useRef();

    const handleSubmit = event => {
        event.preventDefault();

        if (validateUserLogin({ usernameRef, passwordRef })) {
            loginUser(usernameRef.current.value, passwordRef.current.value)
                .then(data => {
                    setCookie('JWT', 'Bearer ' + data.jwtToken,
                        { path: '/', maxAge: 259200, sameSite: 'none', secure: 'None' })
                })
                .then(() => navigate('/recipes'));
        }

    }

    const getEmailFromUserInput = async () => {
        const { value: email } = await Swal.fire({
            title: 'Input email address',
            input: 'email',
            inputLabel: 'Type your email address so that we can reach you.',
            inputPlaceholder: 'Enter your email address'
        })

        if (email) {
            sendResetPasswordRequest(email);
        }
    }

    const handleForgotPassword = e => {
        e.preventDefault();

        getEmailFromUserInput();
    }

    useEffect(() => {
        validateUser(cookies)
            .then(isValid => setIsLogged(isValid))
            .finally(setHasLoaded(true));
    }, []);

    if (!hasLoaded) return (
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
                                    ref={usernameRef}
                                    placeholder=" "
                                />
                                <label htmlFor="username">Username</label>
                                <div className="error"></div>
                            </div>
                            <div className='field'>
                                <input
                                    id="password"
                                    type="password"
                                    ref={passwordRef}
                                    placeholder=" "
                                />
                                <label htmlFor="password">Password</label>
                                <div className="error"></div>
                            </div>
                            <div className='options'>
                                <a onClick={handleForgotPassword}>Forgot Password?</a>
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