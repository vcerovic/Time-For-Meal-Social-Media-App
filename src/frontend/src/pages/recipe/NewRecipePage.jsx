import React from 'react'
import { useEffect, useRef, useState } from 'react'
import { useCookies } from "react-cookie";
import { capitalizeFirstLetter } from '../../utils/StringUtils';
import jwt_decode from "jwt-decode";

const NewRecipePage = () => {
    const [categories, setCategories] = useState([]);
    const [ingredients, setIngredients] = useState([]);
    const [selectedIngredients, setSelectedIngredients] = useState([]);
    const [cookies, setCookie] = useCookies();
    let currentUsername = "";
    if (cookies.JWT != null) {
        currentUsername = jwt_decode(cookies.JWT);
    }

    const nameRef = useRef();
    const prepTime = useRef();
    const cookTime = useRef();
    const serving = useRef();
    const image = useRef();
    const recipeCategoryRef = useRef();
    const ingredientsSearchRef = useRef();
    const ingredientsRef = useRef();
    const instructionRef = useRef();

    const handleSubmit = event => {
        event.preventDefault();
        console.log('name:', nameRef.current.value);
        console.log(recipeCategoryRef.current.value);
    }

    const getAllRecipeCategories = async () => {
        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/api/v1/recipes/categories`);
            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message);
            }

            setCategories(data);
        } catch (err) {
            alert(err.message);
        }
    }

    const getAllIngredients = async (prefix) => {
        try {
            const response =
                await fetch(`${process.env.REACT_APP_API_URL}/api/v1/recipes/ingredients?prefix=${prefix}`);
            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message);
            }

            setIngredients(data);
        } catch (err) {
            alert(err.message);
        }
    }


    const searchIngredients = () => {
        if (ingredientsSearchRef.current.value.length > 2) {
            getAllIngredients(ingredientsSearchRef.current.value);
        }
    }

    const handleSelectIngredients = e => {
        let options = e.target.options;
        for (var i = 0, l = options.length; i < l; i++) {
            if (options[i].selected) {
                setSelectedIngredients([...selectedIngredients, {
                    "value": options[i].value,
                    "name": options[i].innerText
                }])
            }
        }
    }

    useEffect(() => {
        getAllRecipeCategories();
    }, []);

    if (currentUsername === "") {
        return <p>You must log in!</p>
    } else {
        return (
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="name">Name:</label>
                    <input id="name" type="text" ref={nameRef} />
                </div>
                <div>
                    <label htmlFor="prepTime">Preparation time:</label>
                    <input id="prepTime" type="number" ref={prepTime} />
                </div>
                <div>
                    <label htmlFor="cookTime">Cook time:</label>
                    <input id="cookTime" type="number" ref={cookTime} />
                </div>
                <div>
                    <label htmlFor="serving">Serving:</label>
                    <input id="serving" type="number" ref={serving} />
                </div>
                <div>
                    <input type="file" id="image" ref={image} accept="image/png, image/jpeg" className="input_file" />
                    <label htmlFor="image">Choose a image:</label>
                </div>
                <div>
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
                <div>
                    <label htmlFor="ingredientsIds">Ingredients:</label>
                    <div>
                        <p>Search for ingredients</p>
                        <input type="text" id="ingredientsSearch"
                            ref={ingredientsSearchRef}
                            onChange={searchIngredients} />
                        <select id='ingredients' ref={ingredientsRef} onChange={handleSelectIngredients} multiple>
                            {ingredients.map(ingredient =>
                                <option
                                    key={ingredient.id}
                                    value={ingredient.id}>{capitalizeFirstLetter(ingredient.name)}
                                </option>
                            )}
                        </select>
                    </div>
                </div>
                <div>
                    Selected ingredients:
                    {
                        selectedIngredients.map(selectedIng => 
                        <p key={selectedIng.value}>{selectedIng.name}
                        </p>)
                    }
                </div>
                <div>
                    <label htmlFor="instruction">Instructions:</label>
                    <textarea id="instruction" ref={instructionRef} />
                </div>
                <button type="submit">Submit</button>
            </form>
        );
    }
}

export default NewRecipePage