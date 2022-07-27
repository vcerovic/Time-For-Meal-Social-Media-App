package com.veljkocerovic.timeformeal.api.like;

import com.veljkocerovic.timeformeal.api.recipe.Recipe;
import com.veljkocerovic.timeformeal.api.recipe.RecipeRepository;
import com.veljkocerovic.timeformeal.api.recipe.RecipeService;
import com.veljkocerovic.timeformeal.api.user.AppUser;
import com.veljkocerovic.timeformeal.api.user.AppUserService;
import com.veljkocerovic.timeformeal.api.user.UserRepository;
import com.veljkocerovic.timeformeal.exceptions.RecipeNotFoundException;
import com.veljkocerovic.timeformeal.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void likeRecipe(Integer recipeId) throws UserNotFoundException, RecipeNotFoundException {
        //Get active user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = appUserService.findUserByUsername(authentication.getName());

        //Get recipe
        Recipe recipe = recipeService.getRecipeById(recipeId);


        List<AppUser> userLikes = recipe.getUsersLikes();

        if(userLikes.contains(user)){
            //If user already liked recipe then remove like
            userLikes.remove(user);
        } else {
            //Add new like
            userLikes.add(user);
        }


        List<Recipe> likedRecipes = user.getRecipesLikes();
        if(likedRecipes.contains(recipe)){
            likedRecipes.remove(recipe);
        } else {
            likedRecipes.add(recipe);
        }

        recipe.setUsersLikes(userLikes);
        user.setRecipesLikes(likedRecipes);

        userRepository.save(user);
    }

    @Override
    public int countAllLikes(Integer recipeId) throws RecipeNotFoundException {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        return recipe.getUsersLikes().size();
    }
}
