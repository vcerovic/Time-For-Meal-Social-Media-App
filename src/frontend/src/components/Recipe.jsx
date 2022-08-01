import React, { useState, useEffect, useRef } from 'react'
import { useCookies } from 'react-cookie';
import { validateUser } from '../api/AuthApi';
import { Rating } from 'react-simple-star-rating'
import { getRecipe, getRecipeImage, calculateRating, createComment, rateRecipe, likeRecipe } from '../api/RecipeApi';
import { capitalizeFirstLetter, replaceWithBr } from '../utils/StringUtils';
import { getUserByUsername, getUserImage } from '../api/UserApi';
import Heart from "react-animated-heart";
import jwt_decode from "jwt-decode";
import Comment from './Comment.jsx';

const Recipe = ({ recipeId }) => {
    const [cookies, setCookie, removeCookie] = useCookies();
    const [recipe, setRecipe] = useState({});
    const [recipeImage, setRecipeImage] = useState();
    const [userImage, setUserImage] = useState();
    const [hasLoaded, setHasLoaded] = useState(false);
    const [isLogged, setIsLogged] = useState(false);
    const [isLiked, setIsLiked] = useState(false);
    const [user, setUser] = useState(null);
    const [rating, setRating] = useState(0)
    const [isExecuted, setIsExecuted] = useState(false);

    const commentRef = useRef();

    const handleCommentSubmit = e => {
        e.preventDefault();

        const formData = new FormData();
        formData.append('comment', commentRef.current.value);

        createComment(recipeId, formData, cookies.JWT)
            .then(() => { getRecipe(recipeId).then(data => setRecipe(data)) })
    }

    const handleLikeRecipe = () => {
        setIsLiked(!isLiked);
        likeRecipe(recipeId, cookies.JWT)
            .then(() => { getRecipe(recipeId).then(data => setRecipe(data)) })
    }

    const handleRating = rate => {
        setRating(rate);

        const formData = new FormData();
        formData.append('rating', ((rate / 2) / 10));

        rateRecipe(recipeId, formData, cookies.JWT)
            .then(() => { getRecipe(recipeId).then(data => setRecipe(data)) })
    }

    const updateRecipe = () => {
        getRecipe(recipeId)
            .then(data => {
                setRecipe(data)
            })
    }

    useEffect(() => {
        getRecipe(recipeId)
        .then(data => {
            setRecipe(data)
            getUserImage(data.owner.id)
                .then(image => {
                    setUserImage(image)
                    getRecipeImage(recipeId)
                        .then(image => setRecipeImage(image))
                        .finally(setHasLoaded(true))
                })

        })
        .then(() => {
            validateUser(cookies)
                .then(isValid => {
                    setIsLogged(isValid)
                    if (isValid) {
                        let decoded = jwt_decode(cookies.JWT);
                        getUserByUsername(decoded.sub)
                            .then(data => {
                                setUser(data)
                            })
                    }
                })
        })
    }, []);

    if (hasLoaded && user && recipe && !isExecuted) {
        recipe.ratings.forEach(rating => rating.appUser.id === user.id ? setRating(rating.rating * 20) : null)
        recipe.likes.forEach(like => like.id === user.id ? setIsLiked(true) : null)
        setIsExecuted(true);
    }


    if (!hasLoaded) {
        console.log("2")
        return (
            <div id="preloader">
                <div id="loader"></div>
            </div>
        )
    } else {
        console.log("3")
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

                    <div className='rate'>
                        <h3 className='title'>Rate</h3>
                        <div>
                            <Rating onClick={handleRating} ratingValue={rating} size={30} />
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
                    <div className='thumbnail'>
                        <Heart isClick={isLiked} onClick={handleLikeRecipe} />
                        <img src={recipeImage} alt={recipe.name} />
                    </div>

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
                                <span className="value green">{recipe.likes.length}</span>
                                <p className="title">Likes</p>
                            </li>
                            <li className="recipe-details-item time ">
                                <i className="fa-regular fa-star yellow"></i>
                                <span className="value yellow">{calculateRating(recipe.ratings)}</span>
                                <p className="title">Rating</p>
                            </li>
                            <li className="recipe-details-item time">
                                <i className="fa-regular fa-comment  red"></i>
                                <span className="value red">{recipe.comments.length}</span>
                                <p className="title">Comments</p>
                            </li>
                        </ul>
                        <div>
                            <h2>Insturcitons:</h2>
                            <p dangerouslySetInnerHTML={{ __html: replaceWithBr(recipe.instruction) }} />
                        </div>
                    </div>

                    <div className='comments'>
                        <h2>Comments:</h2>
                        <div>
                            {isLogged ?
                                <form onSubmit={handleCommentSubmit}>
                                    <textarea ref={commentRef} name="commentText" id="commentText" cols="30" rows="3"></textarea>
                                    <input className='linkBtn' type="submit" value="Comment" />
                                </form>
                                : <div></div>}

                            {recipe.comments != null ?
                                recipe.comments.map(comment => <Comment key={comment.id} updateRecipe={updateRecipe} comment={comment} />)
                                : <div>No comments </div>}
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default Recipe