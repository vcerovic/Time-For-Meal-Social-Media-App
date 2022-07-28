package com.veljkocerovic.timeformeal.api.like;


import com.veljkocerovic.timeformeal.exceptions.RecipeNotFoundException;
import com.veljkocerovic.timeformeal.exceptions.UserNotFoundException;
import com.veljkocerovic.timeformeal.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/recipe")
public class LikeController {

    @Autowired
    private LikeService likeService;


    @GetMapping("/{id}/like")
    public int getLikesCount(@PathVariable(value = "id") Integer recipeId) throws RecipeNotFoundException {
        return likeService.countAllLikes(recipeId);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<ResponseMessage> likeRecipe(@PathVariable(value = "id") Integer recipeId)
            throws UserNotFoundException, RecipeNotFoundException {
        likeService.likeRecipe(recipeId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseMessage("Successfully liked"));
    }
}
