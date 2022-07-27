package com.veljkocerovic.timeformeal.api.comment;

import com.veljkocerovic.timeformeal.exceptions.RecipeNotFoundException;
import com.veljkocerovic.timeformeal.exceptions.UserNotFoundException;

import java.util.List;

public interface RecipeCommentService {

    void commentRecipe(Integer recipeId, String comment) throws UserNotFoundException, RecipeNotFoundException;

    void deleteComment(Integer recipeId) throws UserNotFoundException, RecipeNotFoundException;

    List<RecipeComment> getAllComments(Integer recipeId);
}
