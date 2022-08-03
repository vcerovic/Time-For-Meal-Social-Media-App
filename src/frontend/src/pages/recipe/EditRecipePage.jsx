import React from 'react'
import { useEffect, useRef, useState } from 'react'
import { getAllRecipeCategories, getAllIngredients, getRecipe, getRecipeImage, updateRecipe, getRecipeImageFile } from '../../api/RecipeApi';
import { validateUser } from '../../api/AuthApi';
import { useCookies } from "react-cookie";
import { capitalizeFirstLetter } from '../../utils/StringUtils';
import { useNavigate, useLocation, useParams } from 'react-router-dom';
import jwt_decode from "jwt-decode";
import { getUserByUsername } from '../../api/UserApi';
import { blobToFile } from '../../utils/FileUtils';
import { validateRecipe } from '../../utils/ValidationUtils';

const EditRecipePage = () => {
    const [categories, setCategories] = useState([]);
    const [user, setUser] = useState({});
    const [recipe, setRecipe] = useState({});
    const [ingredients, setIngredients] = useState([]);
    const [selectedIngredients, setSelectedIngredients] = useState([]);
    const [cookies, setCookie] = useCookies();
    const [hasLoaded, setHasLoaded] = useState(false);
    const navigate = useNavigate();
    const params = useParams();

    const nameRef = useRef();
    const prepTimeRef = useRef();
    const cookTimeRef = useRef();
    const servingRef = useRef();
    const imageRef = useRef();
    const recipeCategoryRef = useRef();
    const ingredientsSearchRef = useRef();
    const ingredientsRef = useRef();
    const instructionRef = useRef();

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

    const handleUpdateRecipe = e => {
        e.preventDefault();
        let isEditForm = true;

        if (validateRecipe({
            nameRef, prepTimeRef, cookTimeRef,
            servingRef, imageRef, instructionRef, selectedIngredients, ingredientsRef, isEditForm
        })) {
            const formData = new FormData();
            formData.append('name', nameRef.current.value);
            formData.append('instruction', instructionRef.current.value);
            formData.append('prepTime', prepTimeRef.current.value);
            formData.append('cookTime', cookTimeRef.current.value);
            formData.append('serving', servingRef.current.value);
            formData.append('recipeCategoryId', recipeCategoryRef.current.value);

            getRecipeImageFile(recipe.id)
                .then(image => {
                    imageRef.current.files[0]
                        ? formData.append('image', imageRef.current.files[0])
                        : formData.append('image', blobToFile(image, recipe.name));

                    updateRecipe(recipe.id, formData, selectedIngredients, cookies.JWT)
                        .then(success => success ? navigate(`/recipes/${recipe.id}`) : {});
                })
        }
    }

    useEffect(() => {
        getAllRecipeCategories()
            .then(data => setCategories(data))

        validateUser(cookies)
            .then(isValid => {
                if (isValid) {
                    let decoded = jwt_decode(cookies.JWT);
                    getUserByUsername(decoded.sub)
                        .then(data => setUser(data))
                }
            })
            .then(() => {
                getRecipe(params.recipeId)
                    .then(data => {
                        setRecipe(data);
                        let tempIng = []
                        data.ingredients.map(ing => {
                            tempIng.push({
                                "value": ing.id,
                                "name": capitalizeFirstLetter(ing.name)
                            })
                        })
                        setSelectedIngredients(tempIng);
                    })
                    .finally(setHasLoaded(true));
            })

    }, []);

    if (!hasLoaded) {
        return (
            <div id="preloader">
                <div id="loader"></div>
            </div>
        )
    } else {
        if (user && recipe.owner) {
            if (user.id === recipe.owner.id) {
                return (
                    <div id='recipePage'>
                        <div className='form-container'>
                            <h1 className='title'>Edit recipe</h1>
                            <form onSubmit={handleUpdateRecipe}>
                                <div className='field'>
                                    <input id="name" type="text" placeholder=' '
                                        defaultValue={recipe.name} ref={nameRef} />
                                    <label htmlFor="name">Name:</label>
                                    <div className="error"></div>
                                </div>
                                <div className='field'>
                                    <input id="prepTime" type="text" placeholder=' '
                                        defaultValue={recipe.prepTime} ref={prepTimeRef} />
                                    <label htmlFor="prepTime">Preparation time:</label>
                                    <div className="error"></div>
                                </div>
                                <div className='field'>
                                    <input id="cookTime" type="text" placeholder=' '
                                        defaultValue={recipe.cookTime} ref={cookTimeRef} />
                                    <label htmlFor="cookTime">Cook time:</label>
                                    <div className="error"></div>
                                </div>
                                <div className='field'>
                                    <input id="serving" type="text" placeholder=' '
                                        defaultValue={recipe.serving} ref={servingRef} />
                                    <label htmlFor="serving">Serving:</label>
                                    <div className="error"></div>
                                </div>
                                <div className='field image-field'>
                                    <input type="file" id="image" placeholder=" "
                                        ref={imageRef} accept="image/png, image/jpeg"
                                        className="input_file" />
                                    <label htmlFor="image">Change image</label>
                                    <div className="error"></div>
                                </div>

                                <div className='textarea-fld'>
                                    <label id="instructionLbl" htmlFor="instruction">Instructions:</label>
                                    <textarea id="instruction" placeholder=' ' ref={instructionRef}
                                        defaultValue={recipe.instruction} />
                                    <div className="error"></div>
                                </div>

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
                                                value={ingredient.id}>
                                                {capitalizeFirstLetter(ingredient.name)}
                                            </option>
                                        )}
                                    </select>
                                    <div className="error"></div>
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
                                    <select id='recipeCategoryId' ref={recipeCategoryRef} defaultValue={recipe.recipeCategory.id}>
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
                )
            }

        }
    }
}

export default EditRecipePage