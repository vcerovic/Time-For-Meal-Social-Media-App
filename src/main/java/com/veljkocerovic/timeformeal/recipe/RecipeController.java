package com.veljkocerovic.timeformeal.recipe;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/recipe")
public class RecipeController {

    //GET ALL RECIPES
    @GetMapping
    public List<Recipe> getAllRecipes(){

        return null;
    }

    //CREATE NEW RECIPE
    @PostMapping
    public String saveRecipe(@Valid @RequestBody Recipe recipe){

        return "";
    }

    //GET RECIPE BY ID
    @GetMapping("${id}")
    public Recipe getRecipeById(@PathVariable(value = "id") Integer recipeId){

        return null;
    }

    //DELETE RECIPE
    @DeleteMapping("${id}")
    public String deleteRecipe(@PathVariable(value = "id") Integer recipeId){

        return "";
    }

    //UPDATE RECIPE
    @PutMapping("${id}")
    public String updateRecipe(@PathVariable(value = "id") Integer recipeId,
                               @Valid @RequestBody Recipe recipe){

        return "";
    }

}
