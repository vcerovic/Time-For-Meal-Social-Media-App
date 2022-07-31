
import React, { useState } from 'react'
import { useEffect } from 'react'
import RecipeCard from '../../components/RecipeCard';

const RecipePage = () => {
  const [recipes, setRecipes] = useState([]);

  const getAllRecipes = async () => {
    try {
      const response = await fetch(`${process.env.REACT_APP_API_URL}/api/v1/recipes`);
      const data = await response.json();

      if (!response.ok) {
        throw new Error(data.message);
      }

      setRecipes(data);
    } catch (err) {
      alert(err.message);
    }
  }

  useEffect(() => {
    getAllRecipes();
  }, []);

  if (recipes.length < 1) {
    return (
      <div id="preloader">
        <div id="loader"></div>
      </div>
    )
  } else {
    return (
      <div id='recipePage'>
        {recipes.map(recipe => <RecipeCard key={recipe.id} recipe={recipe} />)}
      </div>
    )
  }
}

export default RecipePage