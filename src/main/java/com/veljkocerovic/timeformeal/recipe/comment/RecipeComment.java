package com.veljkocerovic.timeformeal.recipe.comment;

import com.veljkocerovic.timeformeal.recipe.Recipe;
import com.veljkocerovic.timeformeal.user.model.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "recipe_comments")
@Data
public class RecipeComment {

    @EmbeddedId
    private RecipeCommentKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    private String comment;
}
