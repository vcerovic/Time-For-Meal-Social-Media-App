import Swal from "sweetalert2";
const RECIPE_API_PATH = process.env.REACT_APP_API_URL + '/api/v1/recipes/';

export const getAllRecipes = async () => {
    try {
        const response = await fetch(RECIPE_API_PATH);
        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message);
        }

        return data;
    } catch (err) {
        console.log(err);
    }
}

export const getRecipe = async (recipeId) => {
    try {
        const response = await fetch(`${RECIPE_API_PATH}${recipeId}`);
        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message);
        }

        return data;
    } catch (err) {
        console.log(err);
    }
}

export const getRecipeImage = async (recipeId) => {
    try {
        const response = await fetch(`${RECIPE_API_PATH}${recipeId}/image`);
        const imageBlob = await response.blob();
        const imageObjectURL = await URL.createObjectURL(imageBlob);

        return imageObjectURL;
    } catch (err) {
        console.log(err);
    }

}
export const getRecipeImageFile = async (recipeId) => {
    try {
        const response = await fetch(`${RECIPE_API_PATH}${recipeId}/image`);
        const imageBlob = await response.blob();

        return imageBlob;
    } catch (err) {
        console.log(err);
    }

}

export const createNewRecipe = async (formData, selectedIngredients, jwt) => {
    const ingredientsIdsToSend = [];
    selectedIngredients.forEach(ing => ingredientsIdsToSend.push(ing.value));
    formData.append('ingredientsIds', ingredientsIdsToSend);


    const requestOptions = {
        method: 'POST',
        headers: {
            'Authorization': jwt
        },
        body: formData
    };

    try {
        const response = await fetch(RECIPE_API_PATH, requestOptions);
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

export const getAllRecipeCategories = async () => {
    try {
        const response = await fetch(`${RECIPE_API_PATH}categories`);
        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message);
        }

        return data;
    } catch (err) {
        console.log(err);
    }
}


export const getAllIngredients = async (prefix) => {
    try {
        const response =
            await fetch(`${RECIPE_API_PATH}ingredients/search?prefix=${prefix}`);
        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message);
        }

        return data;
    } catch (err) {
        console.log(err);
    }
}

export const getAllRecipesByName = async (infix) => {
    try {
        const response =
            await fetch(`${RECIPE_API_PATH}search?infix=${infix}`);
        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message);
        }

        return data;
    } catch (err) {
        console.log(err);
    }
}

export const getAllRecipeComments = async (recipeId) => {
    try {
        const response = await fetch(`${RECIPE_API_PATH}${recipeId}/comments`);
        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message);
        }

        return data;
    } catch (err) {
        console.log(err);
    }
}

export const createComment = async (recipeId, formData, jwt) => {

    if (jwt == null) {
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
            'Authorization': jwt
        },
        body: formData
    };

    try {
        const response = await fetch(RECIPE_API_PATH + `${recipeId}/comments`, requestOptions);
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

export const deleteComment = async (recipeId, commentId, jwt) => {

    if (jwt == null) {
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
            'Authorization': jwt
        },
    };

    try {
        const response = await fetch(RECIPE_API_PATH + `${recipeId}/comments/${commentId}`, requestOptions);
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

export const rateRecipe = async (recipeId, formData, jwt) => {
    if (jwt == null) {
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
            'Authorization': jwt
        },
        body: formData
    };

    try {
        const response = await fetch(RECIPE_API_PATH + `${recipeId}/rate`, requestOptions);
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

export const likeRecipe = async (recipeId, jwt) => {
    if (jwt == null) {
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
            'Authorization': jwt
        }
    };

    try {
        const response = await fetch(RECIPE_API_PATH + `${recipeId}/like`, requestOptions);
        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message);
        }

        return true;

    } catch (err) {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: err.message,
        });
    }
}

export const deleteRecipe = async (recipeId, jwt) => {
    if (jwt == null) {
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
            'Authorization': jwt
        }
    };

    try {
        const response = await fetch(RECIPE_API_PATH + `${recipeId}`, requestOptions);
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

        return false;
    }
    
}

export const updateRecipe = async (recipeId, formData, selectedIngredients, jwt) => {
    if (jwt == null) {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: "You must log in",
        });
        return false;
    }

    const ingredientsIdsToSend = [];
    selectedIngredients.forEach(ing => ingredientsIdsToSend.push(ing.value));
    formData.append('ingredientsIds', ingredientsIdsToSend);


    const requestOptions = {
        method: 'PUT',
        headers: {
            'Authorization': jwt
        },
        body: formData
    };

    try {
        const response = await fetch(`${RECIPE_API_PATH}${recipeId}`, requestOptions);
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