import React, { useEffect } from 'react'
import { useCookies } from 'react-cookie';
import jwt_decode from "jwt-decode";

const LOGIN_PATH = process.env.REACT_APP_API_URL + '/auth/login';

const LoginPage = () => {
    const [username, setUsername] = React.useState('');
    const [password, setPassword] = React.useState('');
    const [cookies, setCookie] = useCookies(['jwt']);
    const [isLogged, setIsLogged] = React.useState(false);

    function handleSubmit(event) {
        event.preventDefault();
        loginUser();
    }

    const validateUserToken = async () => {
        const requestOptions = {
            method: 'POST',
            headers: {
                'Authorization': cookies.JWT
            }
        };

        try {
            const response = await fetch(process.env.REACT_APP_API_URL + '/auth/validate', requestOptions);

            if (!response.ok) {
                throw new Error(response);
            }

            setIsLogged(true);
        } catch (err) {
            setIsLogged(false);
        
        }
    }

    const loginUser = async () => {
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username: username, password: password })
        };



        try {
            const response = await fetch(LOGIN_PATH, requestOptions);
            const data = await response.json();


            if (!response.ok) {
                throw new Error(data.message);
            }

            setCookie('JWT', 'Bearer ' + data.jwtToken,
                { path: '/', maxAge: 259200, sameSite: 'none', secure: 'None' })

        } catch (err) {
            alert(err.message);
        }
    }

    useEffect(() => {
        if (cookies.JWT != null) {
            validateUserToken();
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