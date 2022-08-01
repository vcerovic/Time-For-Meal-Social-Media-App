package com.veljkocerovic.timeformeal.api.recipe;

import com.veljkocerovic.timeformeal.api.ingredient.Ingredient;
import com.veljkocerovic.timeformeal.exceptions.ImageSizeLimitException;
import com.veljkocerovic.timeformeal.exceptions.RecipeNotFoundException;
import com.veljkocerovic.timeformeal.exceptions.UserNotFoundException;
import com.veljkocerovic.timeformeal.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    //GET ALL RECIPES
    @GetMapping
    public List<Recipe> getAllRecipes(){
        return recipeService.getAllRecipes();
    }

    @GetMapping("/search")
    public List<Recipe> getAllRecipesByName(@RequestParam("infix") String infix){
        return recipeService.getAllRecipesByName(infix);
    }

    //CREATE NEW RECIPE
    @PostMapping
    public ResponseEntity<ResponseMessage> saveRecipe(@Valid @ModelAttribute RecipeModel recipeModel,
                                                      Authentication authentication) throws UserNotFoundException, ImageSizeLimitException {
        recipeService.saveRecipe(recipeModel, authentication);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseMessage("Recipe successfully created"));
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
    public ResponseEntity<ResponseMessage> deleteRecipe(@PathVariable(value = "id") Integer recipeId) throws
            RecipeNotFoundException {
        recipeService.deleteRecipe(recipeId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("Recipe successfully deleted."));

    }

    //UPDATE RECIPE
    @PreAuthorize("@securityService.isRecipeOwner(#recipeId)")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> updateRecipe(@PathVariable(value = "id") Integer recipeId,
                               @Valid @ModelAttribute RecipeModel recipeModel) throws
            RecipeNotFoundException,
            ImageSizeLimitException {
        recipeService.updateRecipe(recipeId, recipeModel);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("Recipe successfully updated."));
    }

    @GetMapping(
            value = "/{id}/image",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE}
    )
    public @ResponseBody byte[] getRecipeImage(@PathVariable(value = "id") Integer recipeId)
            throws RecipeNotFoundException {
        return recipeService.getRecipeImage(recipeId);
    }

}
