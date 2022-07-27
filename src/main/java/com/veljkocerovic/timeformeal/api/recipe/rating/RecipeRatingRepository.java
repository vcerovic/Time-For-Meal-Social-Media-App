package com.veljkocerovic.timeformeal.api.recipe.rating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecipeRatingRepository extends JpaRepository<RecipeRating, RecipeRatingKey> {
    Optional<RecipeRating> findById(Integer recipeId);

    @Query("FROM RecipeRating as r where r.recipe.id = :recipeId")
    List<RecipeRating> findAllByRecipeId(@Param("recipeId") Integer recipeId);
}
