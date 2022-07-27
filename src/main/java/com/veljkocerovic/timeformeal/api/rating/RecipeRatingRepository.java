package com.veljkocerovic.timeformeal.api.rating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRatingRepository extends JpaRepository<RecipeRating, RecipeRatingKey> {
    @Query("FROM RecipeRating as r where r.recipe.id = :recipeId")
    List<RecipeRating> findAllByRecipeId(@Param("recipeId") Integer recipeId);
}
