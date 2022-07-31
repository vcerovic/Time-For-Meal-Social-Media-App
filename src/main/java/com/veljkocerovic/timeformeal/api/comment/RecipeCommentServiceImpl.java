package com.veljkocerovic.timeformeal.api.comment;

import com.veljkocerovic.timeformeal.api.recipe.Recipe;
import com.veljkocerovic.timeformeal.api.recipe.RecipeService;
import com.veljkocerovic.timeformeal.api.user.AppUser;
import com.veljkocerovic.timeformeal.api.user.AppUserService;
import com.veljkocerovic.timeformeal.exceptions.RecipeNotFoundException;
import com.veljkocerovic.timeformeal.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecipeCommentServiceImpl implements RecipeCommentService{

    @Autowired
    private RecipeCommentRepository commentRepository;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private RecipeService recipeService;

    @Override
    public void commentRecipe(Integer recipeId, String comment) throws UserNotFoundException, RecipeNotFoundException {
        //Get active user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = appUserService.findUserByUsername(authentication.getName());

        //Get recipe
        Recipe recipe = recipeService.getRecipeById(recipeId);

        //Create comment
        RecipeComment recipeComment = new RecipeComment();
        recipeComment.setAppUser(user);
        recipeComment.setRecipe(recipe);
        recipeComment.setComment(comment);
        recipeComment.setCreatedAt(LocalDateTime.now());


        //Save comment
        commentRepository.save(recipeComment);
    }

    @Override
    public void deleteComment(Integer recipeId) throws UserNotFoundException, RecipeNotFoundException {
        //Get active user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = appUserService.findUserByUsername(authentication.getName());

        //Get recipe
        Recipe recipe = recipeService.getRecipeById(recipeId);
    }

    @Override
    public List<RecipeComment> getAllComments(Integer recipeId) {
        return commentRepository.findAllByRecipeId(recipeId);
    }
}
