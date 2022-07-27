package com.veljkocerovic.timeformeal.api.comment;

import com.veljkocerovic.timeformeal.exceptions.RecipeNotFoundException;
import com.veljkocerovic.timeformeal.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recipe")
public class RecipeCommentController {

    @Autowired
    private RecipeCommentService commentService;

    //GET ALL COMMENTS
    @GetMapping("/{id}/comments")
    public List<RecipeComment> getAllRecipeComments(@PathVariable(value = "id") Integer recipeId){
        return commentService.getAllComments(recipeId);
    }

    //COMMENT RECIPE
    @PostMapping("/{id}/comment")
    public String commentRecipe(@PathVariable(value = "id") Integer recipeId,
                                @RequestParam String comment) throws
            UserNotFoundException, RecipeNotFoundException {
        commentService.commentRecipe(recipeId, comment);
        return "Comment successfully created.";
    }

}
