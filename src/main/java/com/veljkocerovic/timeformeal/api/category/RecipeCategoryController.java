package com.veljkocerovic.timeformeal.api.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recipes/categories")
public class RecipeCategoryController {

    @Autowired
    private RecipeCategoryService recipeCategoryService;

    @GetMapping
    public List<RecipeCategory> getAllCategories(){
        return recipeCategoryService.getAllRecipeCategories();
    }
}
