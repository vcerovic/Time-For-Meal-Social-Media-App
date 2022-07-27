package com.veljkocerovic.timeformeal.api.like;

import com.veljkocerovic.timeformeal.exceptions.RecipeNotFoundException;
import com.veljkocerovic.timeformeal.exceptions.UserNotFoundException;

public interface LikeService {

    void likeRecipe(Integer recipeId) throws UserNotFoundException, RecipeNotFoundException;

    int countAllLikes(Integer recipeId) throws RecipeNotFoundException;
}
