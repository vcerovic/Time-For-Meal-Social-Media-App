
import React, { useState } from 'react'
import { useEffect } from 'react'

import Recipe from '../../components/Recipe'


const RecipePage = () => {
  const [recipes, setRecipes] = useState([]);

  const getAllRecipes = async () => {
    const response = await fetch(`${process.env.REACT_APP_API_URL}/api/v1/recipe`);
    const data = await response.json();

    setRecipes(data);
  }

  useEffect(() => {
    getAllRecipes();
  }, []);

  if (recipes.length < 1) {
    return <p>waiting...</p>
  } else {
    return (
      <div>
        {recipes.map(recipe => <Recipe key={recipe.id} recipe={recipe} />)}
      </div>
    )
  }
}

export default RecipePage