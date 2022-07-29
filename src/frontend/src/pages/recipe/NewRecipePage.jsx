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
        createNewRecipe();
    }

    const createNewRecipe = async () => {
        const ingredientsIdsToSend = [];
        selectedIngredients.forEach(ing => ingredientsIdsToSend.push(ing.value));

        const formData  = new FormData();
        formData.append('name', nameRef.current.value);
        formData.append('instruction', instructionRef.current.value);
        formData.append('prepTime', prepTimeRef.current.value);
        formData.append('cookTime', cookTimeRef.current.value);
        formData.append('serving', servingRef.current.value);
        formData.append('recipeCategoryId', recipeCategoryRef.current.value);
        formData.append('image', imageRef.current.files[0]);
        formData.append('ingredientsIds', ingredientsIdsToSend);


        const requestOptions = {
            method: 'POST',
            headers: { 
                'Authorization': cookies.JWT
            },
            body: formData
        };

        formData.forEach(data => console.log(data));

        try{
            const response = await fetch(process.env.REACT_APP_API_URL + '/api/v1/recipes', requestOptions);
            const data = await response.json();
            
            if (!response.ok) {
                throw new Error(data.message);
            }
            
            alert(data.message)
        } catch(err){
            alert(err.message);
        }
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
                    <input id="prepTime" type="number" ref={prepTimeRef} />
                </div>
                <div>
                    <label htmlFor="cookTime">Cook time:</label>
                    <input id="cookTime" type="number" ref={cookTimeRef} />
                </div>
                <div>
                    <label htmlFor="serving">Serving:</label>
                    <input id="serving" type="number" ref={servingRef} />
                </div>
                <div>
                    <input type="file" id="image" ref={imageRef} accept="image/png, image/jpeg" className="input_file" />
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