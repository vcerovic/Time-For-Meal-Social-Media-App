package com.veljkocerovic.timeformeal.api.recipe;

import com.veljkocerovic.timeformeal.exceptions.ImageSizeLimitException;
import com.veljkocerovic.timeformeal.exceptions.RecipeNotFoundException;
import com.veljkocerovic.timeformeal.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    //GET ALL RECIPES
    @GetMapping
    public List<Recipe> getAllRecipes(){
        return recipeService.getAllRecipes();
    }

    //CREATE NEW RECIPE
    @PostMapping
    public String saveRecipe(@Valid @ModelAttribute RecipeModel recipeModel,
                             Authentication authentication) throws UserNotFoundException, ImageSizeLimitException {
        recipeService.saveRecipe(recipeModel, authentication);
        return "Recipe successfully created";
    }

    //GET RECIPE BY ID
    @GetMapping("/{id}")
    public Recipe getRecipeById(@PathVariable(value = "id") Integer recipeId) throws
            RecipeNotFoundException {
        return recipeService.getRecipeById(recipeId);
    }

    //DELETE RECIPE
    @PreAuthorize("@securityService.isRecipeOwner(#recipeId)")
    @DeleteMapping("/{id}")
    public String deleteRecipe(@PathVariable(value = "id") Integer recipeId) throws
            RecipeNotFoundException {
        recipeService.deleteRecipe(recipeId);
        return "Recipe successfully deleted.";
    }

    //UPDATE RECIPE
    @PreAuthorize("@securityService.isRecipeOwner(#recipeId)")
    @PutMapping("/{id}")
    public String updateRecipe(@PathVariable(value = "id") Integer recipeId,
                               @Valid @ModelAttribute RecipeModel recipeModel) throws
            RecipeNotFoundException,
            ImageSizeLimitException {
        recipeService.updateRecipe(recipeId, recipeModel);
        return "Recipe successfully updated.";
    }

}
