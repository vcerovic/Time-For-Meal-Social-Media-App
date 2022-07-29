import React from 'react'

const REGISTRATION_PATH = process.env.REACT_APP_API_URL + '/registration/register';

const RegistrationPage = () => {
    const [username, setUsername] = React.useState('');
    const [email, setEmail] = React.useState('');
    const [password, setPassword] = React.useState('');

    function handleSubmit(event) {
        event.preventDefault();
        registerUser();
    }

    const registerUser = async () => {
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username: username, email: email, password: password })
        };

        

        try{
            const response = await fetch(REGISTRATION_PATH, requestOptions);
            const data = await response.json();
            
            if (!response.ok) {
                throw new Error(data.message);
            }
            
            alert(data.message)
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
                <label htmlFor="email">Email</label>
                <input
                    id="email"
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
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

export default RegistrationPage