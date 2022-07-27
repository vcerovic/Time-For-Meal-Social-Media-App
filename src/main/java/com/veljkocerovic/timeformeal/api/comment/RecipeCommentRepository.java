package com.veljkocerovic.timeformeal.api.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeCommentRepository extends JpaRepository<RecipeComment, RecipeCommentKey> {
    @Query("FROM RecipeComment as r where r.recipe.id = :recipeId")
    List<RecipeComment> findAllByRecipeId(@Param("recipeId") Integer recipeId);
}
