import Swal from "sweetalert2";
const USER_API_PATH = process.env.REACT_APP_API_URL + '/api/v1/users';

export const getUserById = async (userId) => {
    try {
        const response = await fetch(`${USER_API_PATH}/${userId}`);
        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message);
        }

        return data;
    } catch (err) {
        console.log(err);
    }
}

export const getUserRecipes = async (userId) => {
    try {
        const response = await fetch(`${USER_API_PATH}/${userId}/recipes`);
        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message);
        }

        return data;
    } catch (err) {
        console.log(err);
    }
}

export const getUserByUsername = async (currentUsername) => {
    try {
        const response = await fetch(`${USER_API_PATH}?username=${currentUsername}`);
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

export const getUserImage = async (userId) => {
    try {
        const response =
            await fetch(`${USER_API_PATH}/${userId}/image`);
        const imageBlob = await response.blob();
        const imageObjectURL = await URL.createObjectURL(imageBlob);

        return imageObjectURL;
    } catch (err) {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: err.message,
        });
    }
}

export const getUserImageFile = async (userId) => {
    try {
        const response =
            await fetch(`${USER_API_PATH}/${userId}/image`);
        const imageBlob = await response.blob();
        return imageBlob;
    } catch (err) {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: err.message,
        });
    }
}


export const deleteUser = async (userId, cookies) =>{
    if (!cookies.JWT) {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: "You must log in",
        });
        return false;
    }

    const requestOptions = {
        method: 'DELETE',
        headers: {
            'Authorization': cookies.JWT
        },
    };

    try {
        const response = await fetch(USER_API_PATH + `/${userId}`, requestOptions);
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

export const updateUser = async (userId, formData, cookies) => {
    if (!cookies.JWT) {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: "You must log in",
        });
        return false;
    }

    const requestOptions = {
        method: 'PUT',
        headers: {
            'Authorization': cookies.JWT
        },
        body: formData
    };

    try {
        const response = await fetch(USER_API_PATH + `/${userId}`, requestOptions);
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