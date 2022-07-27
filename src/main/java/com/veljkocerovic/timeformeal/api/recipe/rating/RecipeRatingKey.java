package com.veljkocerovic.timeformeal.api.recipe.rating;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class RecipeRatingKey implements Serializable {

    @Column(name = "user_id")
    private int userId;

    @Column(name = "recipe_id")
    private int recipeId;
}
