package com.veljkocerovic.timeformeal.api.recipe.rating;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.veljkocerovic.timeformeal.api.recipe.Recipe;
import com.veljkocerovic.timeformeal.api.user.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "recipe_ratings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeRating {

    @EmbeddedId
    private RecipeRatingKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private AppUser appUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    @JsonIgnore
    private Recipe recipe;


    private int rating;
}
