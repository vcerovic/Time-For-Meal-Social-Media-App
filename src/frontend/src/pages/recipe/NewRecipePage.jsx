import React from 'react'
import { useEffect, useRef, useState } from 'react'
import { createNewRecipe, getAllRecipeCategories, getAllIngredients } from '../../api/RecipeApi';
import { validateUser } from '../../api/AuthApi';
import { useCookies } from "react-cookie";
import { capitalizeFirstLetter } from '../../utils/StringUtils';
import { useNavigate } from 'react-router-dom';
import jwt_decode from "jwt-decode";

const NewRecipePage = () => {
    const [categories, setCategories] = useState([]);
    const [ingredients, setIngredients] = useState([]);
    const [selectedIngredients, setSelectedIngredients] = useState([]);
    const [cookies, setCookie] = useCookies();
    const [isLogged, setIsLogged] = useState(false);
    const [hasLoaded, setHasLoaded] = useState(false);
    const navigate = useNavigate();


    const nameRef = useRef();
    const prepTimeRef = useRef();
    const cookTimeRef = useRef();
    const servingRef = useRef();
    const imageRef = useRef();
    const recipeCategoryRef = useRef();
    const ingredientsSearchRef = useRef();
    const ingredientsRef = useRef();
    const instructionRef = useRef();

    const handleSubmit = event => {
        event.preventDefault();

        const formData = new FormData();
        formData.append('name', nameRef.current.value);
        formData.append('instruction', instructionRef.current.value);
        formData.append('prepTime', prepTimeRef.current.value);
        formData.append('cookTime', cookTimeRef.current.value);
        formData.append('serving', servingRef.current.value);
        formData.append('recipeCategoryId', recipeCategoryRef.current.value);
        formData.append('image', imageRef.current.files[0]);

        createNewRecipe(formData, selectedIngredients, cookies.JWT)
            .then(() => navigate('/recipes'))
    }



    const searchIngredients = () => {
        if (ingredientsSearchRef.current.value.length > 2) {
            getAllIngredients(ingredientsSearchRef.current.value)
                .then(ingredients => setIngredients(ingredients));
        }
    }

    const handleSelectIngredients = e => {
        let options = e.target.options;
        for (var i = 0, l = options.length; i < l; i++) {
            if (options[i].selected && !selectedIngredients.find(ing => ing.value === options[i].value)) {
                setSelectedIngredients([...selectedIngredients, {
                    "value": options[i].value,
                    "name": options[i].innerText
                }])
            }
        }
    }

    const handleRemoveIngredinet = e => {
        e.preventDefault();
        setSelectedIngredients(selectedIngredients.filter(ing => ing.name !== e.target.innerText))
    }

    useEffect(() => {
        getAllRecipeCategories()
            .then(data => setCategories(data))

        validateUser(cookies)
            .then(isValid => setIsLogged(isValid))
            .finally(setHasLoaded(true));

    }, []);

    if (!hasLoaded) {
        return (
            <div id="preloader">
                <div id="loader"></div>
            </div>
        )
    } else {
        if (!isLogged) return <p>You must log in to create new recipe</p>
        else
            return (
                <div id='recipePage'>
                    <div className='form-container'>
                        <h1 className='title'>New recipe</h1>
                        <form onSubmit={handleSubmit}>
                            <div className='field'>
                                <input id="name" type="text" placeholder=' ' ref={nameRef} />
                                <label htmlFor="name">Name:</label>
                            </div>
                            <div className='field'>
                                <input id="prepTime" type="text" placeholder=' ' ref={prepTimeRef} />
                                <label htmlFor="prepTime">Preparation time:</label>
                            </div>
                            <div className='field'>
                                <input id="cookTime" type="text" placeholder=' ' ref={cookTimeRef} />
                                <label htmlFor="cookTime">Cook time:</label>
                            </div>
                            <div className='field'>
                                <input id="serving" type="text" placeholder=' ' ref={servingRef} />
                                <label htmlFor="serving">Serving:</label>
                            </div>
                            <div className='field image-field'>
                                <input type="file" id="image" placeholder=' ' ref={imageRef} accept="image/png, image/jpeg" className="input_file" />
                                <label htmlFor="image">Choose a image:</label>
                            </div>

                            <label id="instructionLbl" htmlFor="instruction">Instructions:</label>
                            <textarea id="instruction" placeholder=' ' ref={instructionRef} />

                            <button className='linkBtn' type="submit">Submit</button>
                        </form>
                    </div>

                    <div className='form-container'>
                        <h1 className='title'>Info</h1>
                        <form>
                            <div className='field'>
                                <input type="text" id="ingredientsSearch"
                                    placeholder=' ' ref={ingredientsSearchRef}
                                    onChange={searchIngredients} />
                                <label htmlFor="ingredientsSearch">Search for ingredeints:</label>
                            </div>
                            <div className='ingDiv'>
                                <label htmlFor="ingredients">Ingredeints:</label>
                                <select id='ingredients' ref={ingredientsRef} onChange={handleSelectIngredients} multiple>
                                    {ingredients.map(ingredient =>
                                        <option
                                            key={ingredient.id}
                                            value={ingredient.id}>{capitalizeFirstLetter(ingredient.name)}
                                        </option>
                                    )}
                                </select>
                            </div>
                            <div className='selectedIng'>
                                <div>
                                    {
                                        selectedIngredients.map(selectedIng =>
                                            <button onClick={handleRemoveIngredinet}
                                                key={selectedIng.value}>{selectedIng.name}
                                            </button>)
                                    }
                                </div>
                            </div>

                            <div className='recipeCategory'>
                                <label htmlFor="recipeCategoryId">Category:</label>
                                <select id='recipeCategoryId' ref={recipeCategoryRef}>
                                    {categories.map(category =>
                                        <option
                                            key={category.id}
                                            value={category.id}>{capitalizeFirstLetter(category.name)}
                                        </option>
                                    )}
                                </select>
                            </div>
                        </form>

                    </div>

                </div>

            );
    }
}

export default NewRecipePage