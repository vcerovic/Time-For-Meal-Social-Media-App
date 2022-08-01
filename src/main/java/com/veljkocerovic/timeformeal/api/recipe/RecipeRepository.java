package com.veljkocerovic.timeformeal.api.recipe;


import com.veljkocerovic.timeformeal.api.ingredient.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    List<Recipe> findAllByOrderByIdAsc();

    List<Recipe> findByNameContaining(String infix);
    Optional<Recipe> findByName(String name);

    Optional<Recipe> findById(Integer id);

    @Modifying
    @Transactional
    @Query("delete from Recipe r where r.id = ?1")
    void deleteRecipeById(Integer recipeId);
}
