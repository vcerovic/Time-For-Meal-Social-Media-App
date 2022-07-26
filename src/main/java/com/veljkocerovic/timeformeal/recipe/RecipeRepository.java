package com.veljkocerovic.timeformeal.recipe;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    List<Recipe> findAllByOrderByIdAsc();
    Optional<Recipe> findByName(String name);
}
