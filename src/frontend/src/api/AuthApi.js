import Swal from "sweetalert2";

const LOGIN_PATH = process.env.REACT_APP_API_URL + '/auth/login';
const REGISTRATION_PATH = process.env.REACT_APP_API_URL + '/registration';

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
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: err.message,
        });
    }
}

export const registerUser = async (username, email, password) => {
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username: username, email: email, password: password })
    };


    try {
        const response = await fetch(REGISTRATION_PATH + '/register', requestOptions);
        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message);
        }

        Swal.fire({
            title: 'Success',
            icon: 'success',
            text: data.message,
        });
    } catch (err) {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: err.message,
        });
    }
}

export const validateUser = async (cookies) => {
    if (!cookies.JWT) {
        return false;
    }

    try {
        const requestOptions = {
            method: 'POST',
            headers: {
                'Authorization': cookies.JWT
            }
        };

        const response = await fetch(process.env.REACT_APP_API_URL + '/auth/validate', requestOptions);

        if (!response.ok) {
            throw new Error(response);
        }

        return true;
    } catch (err) {
        return false;
    }
}

export const sendResetPasswordRequest = async (email) => {
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email: email })
    };


    try {
        const response = await fetch(REGISTRATION_PATH + '/resetPassword', requestOptions);
        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message);
        }

        Swal.fire({
            title: 'Success',
            icon: 'success',
            text: data.message,
        });
    } catch (err) {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: err.message,
        });
    }
}

export const resetPassword = async (token, newPassword) => {
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ newPassword: newPassword })
    };


    try {
        const response = await fetch(REGISTRATION_PATH + '/savePassword?token=' + token, requestOptions);
        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message);
        }

        Swal.fire({
            title: 'Success',
            icon: 'success',
            text: data.message,
        });
    } catch (err) {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: err.message,
        });
    }
}

export const changePassword = async (oldPassword, newPassword, email, cookies) => {
    if (!cookies.JWT) {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: "You must log in",
        });
        return false;
    }

    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': cookies.JWT
        },
        body: JSON.stringify({
            email: email,
            oldPassword: oldPassword,
            newPassword: newPassword
        })
    };


    try {
        const response = await fetch(REGISTRATION_PATH + '/changePassword', requestOptions);
        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message);
        }

        Swal.fire({
            title: 'Success',
            icon: 'success',
            text: data.message,
        });


        return true;
    } catch (err) {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: err.message,
        });
    }
}