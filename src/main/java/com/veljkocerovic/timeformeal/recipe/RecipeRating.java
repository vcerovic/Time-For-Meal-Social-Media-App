package com.veljkocerovic.timeformeal.recipe;

import com.veljkocerovic.timeformeal.user.User;
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
    private User user;

    @ManyToOne
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;


    private int rating;
}
