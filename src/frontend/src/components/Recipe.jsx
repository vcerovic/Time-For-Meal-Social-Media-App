import React, { useState } from 'react'
import { useEffect } from 'react'

const Recipe = ({ recipeId }) => {
    const [recipe, setRecipe] = useState({});
    const [recipeImage, setRecipeImage] = useState();

    const getRecipe = async () => {
        const response =
            await fetch(`${process.env.REACT_APP_API_URL}/api/v1/recipes/${recipeId}`);
        const data = await response.json();

        setRecipe(data);
    }

    const getRecipeImage = async () => {
        const response =
            await fetch(`${process.env.REACT_APP_API_URL}/api/v1/recipes/${recipeId}/image`);
        const imageBlob = await response.blob();
        const imageObjectURL = await URL.createObjectURL(imageBlob);
        setRecipeImage(imageObjectURL);
    }


    useEffect(() => {
        getRecipe();
        getRecipeImage();
    }, []);

    if (recipe == null || recipeImage == null) {
        return <p>waiting...</p>
    } else {
        return (
            <div>
                <img src={recipeImage} alt={recipe.name} />
                <h1>{recipe.name}</h1>
                <p>{recipe.serving}</p>
            </div>
        )
    }
}

export default Recipe