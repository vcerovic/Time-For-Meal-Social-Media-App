package com.veljkocerovic.timeformeal.services;

import com.veljkocerovic.timeformeal.api.comment.RecipeComment;
import com.veljkocerovic.timeformeal.api.comment.RecipeCommentService;
import com.veljkocerovic.timeformeal.api.recipe.Recipe;
import com.veljkocerovic.timeformeal.api.recipe.RecipeService;
import com.veljkocerovic.timeformeal.api.user.AppUser;
import com.veljkocerovic.timeformeal.api.user.AppUserService;
import com.veljkocerovic.timeformeal.exceptions.CommentNotFoundException;
import com.veljkocerovic.timeformeal.exceptions.RecipeNotFoundException;
import com.veljkocerovic.timeformeal.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("securityService")
public class SecurityService {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeCommentService recipeCommentService;

    private Authentication authentication;

    public boolean isOwner(Integer userId) throws UserNotFoundException {
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = appUserService.findUserById(userId);
        return user.getUsername().equals(authentication.getName());
    }

    public boolean isUserCommentOwner(Integer commentId) throws UserNotFoundException, CommentNotFoundException {
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = appUserService.findUserByUsername(authentication.getName());
        RecipeComment comment = recipeCommentService.findCommentById(commentId);

        return user.getUserComments().contains(comment);
    }

    public boolean isRecipeOwner(Integer recipeId) {
        this.authentication = SecurityContextHolder.getContext().getAuthentication();

        AppUser user;
        try {
            user = appUserService.findUserByUsername(authentication.getName());
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }

        Recipe recipe;

        try {
            recipe = recipeService.getRecipeById(recipeId);
        } catch (RecipeNotFoundException e) {
            throw new RuntimeException(e);
        }


        return user.getRecipes().contains(recipe);
    }
}
