
import React, { useState } from 'react'
import { useEffect } from 'react'
import { Link } from 'react-router-dom';

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
      alert(err);
    }
  }

  useEffect(() => {
    getAllRecipes();
  }, []);

  if (recipes.length < 1) {
    return <p>waiting...</p>
  } else {
    return (
      <div>
        {recipes.map(recipe => (
          <Link
            to={`/recipes/${recipe.id}`}
            key={recipe.id}
          >
            {recipe.name}
          </Link>
        ))}
      </div>
    )
  }
}

export default RecipePage