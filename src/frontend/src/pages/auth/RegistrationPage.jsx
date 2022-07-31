import React, { useState, useEffect } from 'react'
import { useCookies } from 'react-cookie';
import { registerUser, validateUser } from '../../api/AuthApi';

const RegistrationPage = () => {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [cookies, setCookie] = useCookies();
    const [password, setPassword] = useState('');
    const [isLogged, setIsLogged] = useState(false);
    const [hasLoaded, setHasLoaded] = useState(false);


    function handleSubmit(event) {
        event.preventDefault();
        registerUser(username, email, password);
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
        if (isLogged) return <div>You are already registerd.</div>
        else
            return (
                <div id='formPage'>
                    <div className='form-container'>
                        <h1 className='title'>Register</h1>
                        <form onSubmit={handleSubmit}>
                            <div className='field'>
                                <input
                                    id="username"
                                    type="text"
                                    placeholder=' '
                                    value={username}
                                    onChange={(e) => setUsername(e.target.value)}
                                />
                                <label htmlFor="username">Username</label>
                            </div>
                            <div className='field'>
                                <input
                                    id="email"
                                    type="email"
                                    placeholder=' '
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                />
                                <label htmlFor="email">Email</label>
                            </div>
                            <div className='field'>
                                <input
                                    id="password"
                                    type="password"
                                    placeholder=' '
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                />
                                <label htmlFor="password">Password</label>
                            </div>
                            <div className='content'>

                            </div>
                            <button className='linkBtn' type="submit">Submit</button>
                        </form>
                    </div>
                </div>

            )
    }
}

export default RegistrationPage