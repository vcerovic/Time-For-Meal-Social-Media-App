package com.veljkocerovic.timeformeal.api.comment;

import com.veljkocerovic.timeformeal.exceptions.CommentNotFoundException;
import com.veljkocerovic.timeformeal.exceptions.RecipeNotFoundException;
import com.veljkocerovic.timeformeal.exceptions.UserNotFoundException;
import com.veljkocerovic.timeformeal.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recipes")
public class RecipeCommentController {

    @Autowired
    private RecipeCommentService commentService;


    //COMMENT RECIPE
    @PostMapping("/{id}/comments")
    public ResponseEntity<ResponseMessage> commentRecipe(@PathVariable(value = "id") Integer recipeId,
                                                         @RequestParam String comment) throws
            UserNotFoundException, RecipeNotFoundException {
        commentService.commentRecipe(recipeId, comment);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseMessage(
                        "Comment successfully created."));
    }

    @DeleteMapping("/{id}/comments/{commentId}")
    @PreAuthorize("@securityService.isUserCommentOwner(#commentId)")
    public ResponseEntity<ResponseMessage> deleteRecipe(@PathVariable("id") Integer recipeId,
                                                        @PathVariable("commentId") Integer commentId) throws
            UserNotFoundException, RecipeNotFoundException, CommentNotFoundException {

        commentService.deleteComment(recipeId, commentId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage(
                        "Comment successfully deleted."));
    }


    //GET ALL COMMENTS
    @GetMapping("/{id}/comments")
    public List<RecipeComment> getAllRecipeComments(@PathVariable(value = "id") Integer recipeId){
        return commentService.getAllComments(recipeId);
    }

}
