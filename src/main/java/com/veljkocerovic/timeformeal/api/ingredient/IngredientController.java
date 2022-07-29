package com.veljkocerovic.timeformeal.api.ingredient;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/recipes/ingredients")
public class IngredientController {

    @Autowired
    public IngredientService ingredientService;

    @GetMapping
    public List<Ingredient> getAllIngredientsByLetter(@RequestParam("prefix") String prefix){
        return ingredientService.getAllIngredientsByLetter(prefix);
    }
}
