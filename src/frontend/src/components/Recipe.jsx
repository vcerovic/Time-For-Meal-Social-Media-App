import React, { useState } from 'react'
import { useEffect } from 'react'
import { getUserImage } from '../api/UserApi';
import { getRecipe, getRecipeImage, getAllRecipeComments } from '../api/RecipeApi';
import { capitalizeFirstLetter, replaceWithBr } from '../utils/StringUtils';
import Comment from './Comment.jsx';

const Recipe = ({ recipeId }) => {
    const [recipe, setRecipe] = useState({});
    const [comments, setComments] = useState([]);
    const [recipeImage, setRecipeImage] = useState();
    const [userImage, setUserImage] = useState();
    const [hasLoaded, setHasLoaded] = useState(false);


    useEffect(() => {
        getRecipe(recipeId)
            .then(data => {
                setRecipe(data)
                getUserImage(data.owner.id)
                    .then(image => {
                        setUserImage(image)
                        getRecipeImage(recipeId)
                            .then(image => setRecipeImage(image))
                            .then(() => getAllRecipeComments(recipeId))
                            .then(comments => setComments(comments))
                            .finally(setHasLoaded(true))
                    });
            })


    }, []);

    if (!hasLoaded) {
        return (
            <div id="preloader">
                <div id="loader"></div>
            </div>
        )
    } else {
        return (
            <div className='single-recipe'>
                <div>
                    <div className='author'>
                        <h3 className='title'>Author</h3>
                        <div>
                            <img src={userImage} alt={recipe.owner.name} />
                            <h1>{recipe.owner.username}</h1>
                        </div>

                    </div>
                    <div className='ingredients'>
                        <h1 className='title'>Ingredients</h1>
                        <div>
                            {recipe.ingredients.map(ing => <p key={ing.id}>{capitalizeFirstLetter(ing.name)}</p>)}
                        </div>

                    </div>
                </div>

                <div className='content'>
                    <img src={recipeImage} alt={recipe.name} />
                    <div>
                        <h1>{recipe.name}</h1>

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
                            <li className="recipe-details-item time">
                                <i className="fa-regular fa-heart green"></i>
                                <span className="value green">{recipe.cookTime + recipe.prepTime}</span>
                                <p className="title">Likes</p>
                            </li>
                            <li className="recipe-details-item time ">
                                <i className="fa-regular fa-star yellow"></i>
                                <span className="value yellow">{recipe.cookTime + recipe.prepTime}</span>
                                <p className="title">Rating</p>
                            </li>
                            <li className="recipe-details-item time">
                                <i className="fa-regular fa-comment  red"></i>
                                <span className="value red">{recipe.cookTime + recipe.prepTime}</span>
                                <p className="title">Comments</p>
                            </li>
                        </ul>
                        <div>
                            <h3>Insturcitons:</h3>
                            <p dangerouslySetInnerHTML={{ __html: replaceWithBr(recipe.instruction) }} />
                        </div>
                    </div>

                    <div className='comments'>
                        <h1>Comments:</h1>
                        <hr></hr>
                        <div>
                            {comments != null ? 
                            comments.map(comment => <Comment key={comment.id} comment={comment}/>)
                            : <div>No comments </div>}
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default Recipe