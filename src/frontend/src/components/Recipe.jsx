import React, { useState } from 'react'
import { useEffect } from 'react'
import { useCookies } from "react-cookie";

const Recipe = ({ recipeId }) => {
    const [recipe, setRecipe] = useState({});
    const [recipeImage, setRecipeImage] = useState();
    const [cookies, setCookie] = useCookies();

    const getRecipe = async () => {
        try {
            const response = await fetch(
                `${process.env.REACT_APP_API_URL}/api/v1/recipes/${recipeId}`);
            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message);
            }

            setRecipe(data);
        } catch (err) {
            alert(err.message);
        }
    }

    const getRecipeImage = async () => {
        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/api/v1/recipes/${recipeId}/image`);
            const imageBlob = await response.blob();
            const imageObjectURL = await URL.createObjectURL(imageBlob);

            setRecipeImage(imageObjectURL);
        } catch (err) {
            alert(err);
        }

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
            hello
           </div>
        )
    }
}

export default Recipe