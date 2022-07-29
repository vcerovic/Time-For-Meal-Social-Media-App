import React, { useEffect, useState } from 'react'
import { useCookies } from 'react-cookie';
import { validateUserToken, loginUser } from '../../api/AuthApi';

const LoginPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [cookies, setCookie] = useCookies(['jwt']);
    const [isLogged, setIsLogged] = useState(false);

    function handleSubmit(event) {
        event.preventDefault();
        loginUser(username, password)
        .then(data => {
            setCookie('JWT', 'Bearer ' + data.jwtToken,
            { path: '/', maxAge: 259200, sameSite: 'none', secure: 'None' })
        })
    }

    useEffect(() => {
        if (cookies.JWT != null) {
            setIsLogged(validateUserToken(cookies.JWT));
        }
    }, []);

    if (isLogged) {
        return <p>You are already logged in</p>
    } else {
        return (
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="username">Username</label>
                    <input
                        id="username"
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                </div>
                <div>
                    <label htmlFor="password">Password</label>
                    <input
                        id="password"
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>
                <button type="submit">Submit</button>
            </form>
        );
    }
}

export default LoginPage