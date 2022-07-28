package com.veljkocerovic.timeformeal.api.rating;


import com.veljkocerovic.timeformeal.exceptions.RecipeNotFoundException;
import com.veljkocerovic.timeformeal.exceptions.UserNotFoundException;
import com.veljkocerovic.timeformeal.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recipe")
public class RecipeRatingController {

    @Autowired
    private RecipeRatingService recipeRatingService;

    //GET ALL RATINGS
    @GetMapping("/{id}/ratings")
    public List<RecipeRating> getAllRecipeRatings(@PathVariable(value = "id") Integer recipeId){
        return recipeRatingService.getAllRecipeRatings(recipeId);
    }

    //RATE RECIPE
    @PostMapping("/{id}/rate")
    public ResponseEntity<ResponseMessage> rateRecipe(@PathVariable(value = "id") Integer recipeId,
                                                      @RequestParam Integer rating) throws UserNotFoundException, RecipeNotFoundException {
        recipeRatingService.rateRecipe(recipeId, rating);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseMessage("Recipe successfully rated."));
    }

}
