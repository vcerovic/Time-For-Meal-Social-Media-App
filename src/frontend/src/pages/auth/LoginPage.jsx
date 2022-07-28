import React from 'react'
import { useCookies } from 'react-cookie';

const LOGIN_PATH = process.env.REACT_APP_API_URL + '/login';

const LoginPage = () => {
    const [username, setUsername] = React.useState('');
    const [password, setPassword] = React.useState('');
    const [cookies, setCookie] = useCookies(['jwt']);

    function handleSubmit(event) {
        event.preventDefault();
        registerUser();
    }

    const registerUser = async () => {
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username: username, password: password })
        };

        

        try{
            const response = await fetch(LOGIN_PATH, requestOptions);
            const data = await response.json();
    
            
            if (!response.ok) {
                throw new Error(data.message);
            }
            
            setCookie('JWT', 'Bearer ' + data.jwtToken, 
            { path: '/', maxAge: 259200, sameSite: 'none' })

        } catch(err){
            alert(err.message);
        }
       
    }

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

export default LoginPage