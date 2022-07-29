const LOGIN_PATH = process.env.REACT_APP_API_URL + '/auth/login';
const REGISTRATION_PATH = process.env.REACT_APP_API_URL + '/registration/register';

export const loginUser = async (username, password) => {
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

        return data;
    } catch (err) {
        alert(err.message);
    }
}

export const registerUser = async (username, email, password) => {
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

export const validateUserToken = async (jwt) => {
    const requestOptions = {
        method: 'POST',
        headers: {
            'Authorization': jwt
        }
    };

    try {
        const response = await fetch(process.env.REACT_APP_API_URL + '/auth/validate', requestOptions);

        if (!response.ok) {
            throw new Error(response);
        }

        return true;
    } catch (err) {
        return false;
    }
}
