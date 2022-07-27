package com.veljkocerovic.timeformeal.api.recipe.comment;

import com.veljkocerovic.timeformeal.api.recipe.Recipe;
import com.veljkocerovic.timeformeal.api.user.appuser.AppUser;
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
    private AppUser appUser;

    @ManyToOne
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    private String comment;
}
