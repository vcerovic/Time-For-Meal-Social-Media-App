package com.veljkocerovic.timeformeal.api.recipe;


import com.veljkocerovic.timeformeal.exceptions.ImageSizeLimitException;
import com.veljkocerovic.timeformeal.exceptions.RecipeNotFoundException;
import com.veljkocerovic.timeformeal.exceptions.UserNotFoundException;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface RecipeService {

    List<Recipe> getAllRecipes();

    void saveRecipe(RecipeModel recipeModel, Authentication authentication) throws UserNotFoundException, ImageSizeLimitException;

    Recipe getRecipeById(Integer recipeId) throws RecipeNotFoundException;

    void deleteRecipe(Integer recipeId) throws RecipeNotFoundException;

    void updateRecipe(Integer recipeId, Recipe newRecipe);


}
