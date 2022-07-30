import React, { useState, useEffect } from 'react'
import { Link } from 'react-router-dom';
import { getRecipeImage } from '../api/RecipeApi';

const RecipeCard = ({ recipe }) => {
    const [recipeImage, setRecipeImage] = useState();

    useEffect(() => {
        getRecipeImage(recipe.id)
        .then(image => setRecipeImage(image));
    }, []);


    return (
        <div className="recipe-card">
            <div className="thumb">
                <img src={recipeImage} alt={recipe.name} />
            </div>
            <div className="content">
                <header className="header">
                    <div className="row-wrapper">
                        <h2 className="recipe-title">{recipe.name}</h2>
                        <div className="user-rating"></div>
                    </div>
                    <ul className="recipe-details">
                        <li className="recipe-details-item time">
                            <i className="fa-solid fa-clock"></i>
                            <span className="value">{recipe.cookTime + recipe.prepTime}</span>
                            <p className="title">Minutes</p>
                        </li>
                        <li className="recipe-details-item ingredients">
                            <i className="fa-solid fa-lemon"></i>
                            <span className="value">{recipe.ingredients.length}</span>
                            <p className="title">Ingredients</p>
                        </li>
                        <li className="recipe-details-item servings">
                            <i className="fa-solid fa-user"></i>
                            <span className="value">{recipe.serving}</span>
                            <p className="title">Serving</p>
                        </li>
                    </ul>
                </header>
                <footer className="content__footer">
                    <Link className='recipeBtn' to={`/recipes/${recipe.id}`}>View recipe</Link>
                </footer>
            </div>
        </div>
    )
}

export default RecipeCard