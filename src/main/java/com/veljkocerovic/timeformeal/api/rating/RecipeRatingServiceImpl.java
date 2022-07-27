package com.veljkocerovic.timeformeal.api.rating;

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

import java.util.List;

@Service
public class RecipeRatingServiceImpl implements RecipeRatingService{

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeRatingRepository recipeRatingRepository;

    @Override
    public void rateRecipe(Integer recipeId, Integer rating) throws UserNotFoundException, RecipeNotFoundException {
        //Get active user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = appUserService.findUserByUsername(authentication.getName());

        //Get recipe
        Recipe ratedRecipe = recipeService.getRecipeById(recipeId);

        //Create rating
        RecipeRating recipeRating = new RecipeRating(
                new RecipeRatingKey(user.getId(), recipeId),
                user,
                ratedRecipe,
                rating
        );

        //Save rating
        recipeRatingRepository.save(recipeRating);

    }

    @Override
    public List<RecipeRating> getAllRecipeRatings(Integer recipeId) {
        return recipeRatingRepository.findAllByRecipeId(recipeId);
    }
}
