package com.veljkocerovic.timeformeal.api.recipe.ingredient;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    Optional<Ingredient> findById(Integer id);
}
