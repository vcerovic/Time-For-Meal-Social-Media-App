const USER_API_PATH = process.env.REACT_APP_API_URL + '/api/v1/users';

export const getUserByUsername = async (currentUsername) => {
    try {
        const response = await fetch(`${USER_API_PATH}?username=${currentUsername}`);
        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message);
        }

        return data;
    } catch (err) {
        alert(err.message);
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
        alert(err);
    }
}