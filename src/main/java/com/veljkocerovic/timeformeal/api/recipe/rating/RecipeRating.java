package com.veljkocerovic.timeformeal.api.recipe.rating;

import com.veljkocerovic.timeformeal.api.recipe.Recipe;
import com.veljkocerovic.timeformeal.api.user.appuser.AppUser;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "recipe_ratings")
@Data
public class RecipeRating {

    @EmbeddedId
    private RecipeRatingKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    @ManyToOne
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;


    private int rating;
}
