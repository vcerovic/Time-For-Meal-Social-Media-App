package com.veljkocerovic.timeformeal.api.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeCategoryServiceImpl implements RecipeCategoryService{

    @Autowired
    private RecipeCategoryRepository recipeCategoryRepository;

    @Override
    public RecipeCategory getRecipeCategoryById(Integer id) {
        Optional<RecipeCategory> optional = recipeCategoryRepository.findById(id);
        return optional.orElseThrow(() -> new RuntimeException("Category doesn't exist"));
    }

    @Override
    public List<RecipeCategory> getAllRecipeCategories() {
        return recipeCategoryRepository.findAllByOrderByIdAsc();
    }
}
