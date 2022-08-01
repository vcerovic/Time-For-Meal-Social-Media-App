import React, { useState, useEffect } from 'react'
import { Link } from 'react-router-dom';
import { getRecipeImage } from '../api/RecipeApi';

const RecipeCard = ({ recipe }) => {
    const [recipeImage, setRecipeImage] = useState();
    const [hasLoaded, setHasLoaded] = useState(false);

    useEffect(() => {
        getRecipeImage(recipe.id)
            .then(image => setRecipeImage(image))
            .finally(setHasLoaded(true))
    }, []);


    if (!hasLoaded) {
        return (
            <div id="preloader">
                <div id="loader"></div>
            </div>
        )
    } else {
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
                                <i className="fa-solid fa-clock blue"></i>
                                <span className="value blue">{recipe.cookTime + recipe.prepTime}</span>
                                <p className="title">Minutes</p>
                            </li>
                            <li className="recipe-details-item ingredients">
                                <i className="fa-solid fa-lemon purple"></i>
                                <span className="value purple">{recipe.ingredients.length}</span>
                                <p className="title">Ingredients</p>
                            </li>
                            <li className="recipe-details-item servings">
                                <i className="fa-solid fa-user orange"></i>
                                <span className="value orange">{recipe.serving}</span>
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
}

export default RecipeCard