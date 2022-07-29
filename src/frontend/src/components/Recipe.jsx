import React, { useState } from 'react'
import { useEffect } from 'react'
import { getRecipe, getRecipeImage } from '../api/RecipeApi';

const Recipe = ({ recipeId }) => {
    const [recipe, setRecipe] = useState({});
    const [recipeImage, setRecipeImage] = useState();
    const [hasLoaded, setHasLoaded] = useState(false);


    useEffect(() => {
        getRecipe(recipeId)
        .then(data => setRecipe(data))
        .then(() => {
            getRecipeImage(recipeId)
            .then(image => setRecipeImage(image))
        })
        .finally(setHasLoaded(true))
 
    }, []);

    if (!hasLoaded) {
        return <p>Loading...</p>
    } else {
        return (
           <div>
            <h1>{recipe.name}</h1>
            <img src={recipeImage} alt={recipe.name} />
           </div>
        )
    }
}

export default Recipe