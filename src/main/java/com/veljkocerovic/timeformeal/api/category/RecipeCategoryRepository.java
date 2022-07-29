package com.veljkocerovic.timeformeal.api.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Integer> {

    List<RecipeCategory> findAllByOrderByIdAsc();
}
