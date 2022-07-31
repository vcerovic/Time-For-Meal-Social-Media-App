import Swal from "sweetalert2";
const RECIPE_API_PATH = process.env.REACT_APP_API_URL + '/api/v1/recipes/';


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
            type: 'success',
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
        const response = await fetch(`${process.env.REACT_APP_API_URL}/api/v1/recipes/categories`);
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
            await fetch(`${process.env.REACT_APP_API_URL}/api/v1/recipes/ingredients?prefix=${prefix}`);
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

export const calculateRating = (ratings) => {
    if(ratings.length === 0){
        return 0;
    }
    let total = 0;
    ratings.forEach(rating => total += rating.rating);

    return total/ratings.length;
}