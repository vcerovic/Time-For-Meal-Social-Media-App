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
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: err.message,
        });
    }
}

export const getRecipeImage = async (recipeId) => {
    try {
        const response = await fetch(`${RECIPE_API_PATH}${recipeId}/image`);
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