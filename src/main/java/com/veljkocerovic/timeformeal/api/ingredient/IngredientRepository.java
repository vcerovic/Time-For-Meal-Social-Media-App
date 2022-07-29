package com.veljkocerovic.timeformeal.api.ingredient;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    Optional<Ingredient> findById(Integer id);
    List<Ingredient> findByNameStartingWith(String prefix);
}
