package com.veljkocerovic.timeformeal.api.rating;

import com.veljkocerovic.timeformeal.exceptions.RecipeNotFoundException;
import com.veljkocerovic.timeformeal.exceptions.UserNotFoundException;

import java.util.List;

public interface RecipeRatingService {

    void rateRecipe(Integer recipeId, Integer rating) throws UserNotFoundException, RecipeNotFoundException;

    List<RecipeRating> getAllRecipeRatings(Integer recipeId);
}
