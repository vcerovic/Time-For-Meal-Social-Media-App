package com.veljkocerovic.timeformeal.api.comment;

import com.veljkocerovic.timeformeal.exceptions.CommentNotFoundException;
import com.veljkocerovic.timeformeal.exceptions.RecipeNotFoundException;
import com.veljkocerovic.timeformeal.exceptions.UserNotFoundException;

import java.util.List;

public interface RecipeCommentService {

    void commentRecipe(Integer recipeId, String comment) throws UserNotFoundException, RecipeNotFoundException;

    void deleteComment(Integer recipeId, Integer commentId) throws UserNotFoundException, RecipeNotFoundException, CommentNotFoundException;

    RecipeComment findCommentById(Integer commentId) throws CommentNotFoundException;

    List<RecipeComment> getAllComments(Integer recipeId);
}
