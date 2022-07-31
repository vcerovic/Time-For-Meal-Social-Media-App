package com.veljkocerovic.timeformeal.api.rating;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    @ManyToOne
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Recipe recipe;


    private int rating;
}
