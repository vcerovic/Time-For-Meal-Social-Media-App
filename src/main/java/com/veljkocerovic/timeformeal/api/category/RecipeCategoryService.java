package com.veljkocerovic.timeformeal.api.category;

import java.util.List;

public interface RecipeCategoryService {

    RecipeCategory getRecipeCategoryById(Integer id);

    List<RecipeCategory> getAllRecipeCategories();
}
