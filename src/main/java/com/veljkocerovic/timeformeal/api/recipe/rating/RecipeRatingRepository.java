package com.veljkocerovic.timeformeal.api.recipe.rating;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRatingRepository extends JpaRepository<RecipeRating, RecipeRatingKey> {
}
