package com.veljkocerovic.timeformeal.api.recipe;

import com.veljkocerovic.timeformeal.api.user.appuser.AppUser;
import com.veljkocerovic.timeformeal.api.user.appuser.AppUserService;
import com.veljkocerovic.timeformeal.exceptions.RecipeNotFoundException;
import com.veljkocerovic.timeformeal.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService{

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private AppUserService appUserService;


    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAllByOrderByIdAsc();
    }

    @Override
    public void saveRecipe(RecipeModel recipeModel, Authentication authentication) throws UserNotFoundException {
        //Get current user
        AppUser currentUser = appUserService.findUserByUsername(authentication.getName());

        System.out.println(currentUser);
        System.out.println(recipeModel);
    }

    @Override
    public Recipe getRecipeById(Integer recipeId) throws RecipeNotFoundException {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        return optionalRecipe.orElseThrow(() -> new RecipeNotFoundException("Recipe not found"));
    }

    @Override
    public void deleteRecipe(Integer recipeId) throws RecipeNotFoundException {
        Recipe recipe = getRecipeById(recipeId);
        recipeRepository.delete(recipe);
    }

    @Override
    public void updateRecipe(Integer recipeId, Recipe newRecipe) {

    }
}
