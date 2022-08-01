
import React, { useRef, useState } from 'react'
import { useEffect } from 'react'
import { getAllRecipes, getAllRecipesByName } from '../../api/RecipeApi';
import RecipeCard from '../../components/RecipeCard';

const RecipePage = () => {
  const [recipes, setRecipes] = useState([]);
  const searchTextRef = useRef();

  const handleSearch = e => {
    e.preventDefault();

    getAllRecipesByName(searchTextRef.current.value)
    .then(data => setRecipes(data));
  }

  useEffect(() => {
    getAllRecipes()
      .then(data => setRecipes(data));
  }, []);
  

  if (recipes == null) {
    return (
      <div id="preloader">
        <div id="loader"></div>
      </div>
    )
  } else {
    return (
      <div id='recipesPage'>
        <div className="search-form">
          <form onSubmit={handleSearch}>
            <input type="text" ref={searchTextRef} onChange={handleSearch} placeholder='Search recipes...'/>
            <button type="submit"><i className="fa fa-search"></i></button>
          </form>
        </div>
        <div className='recipes'>
          {recipes != null ? recipes.map(recipe => <RecipeCard key={recipe.id} recipe={recipe} />) : <div>No recipes found.</div>}
        </div>

      </div>
    )
  }
}

export default RecipePage