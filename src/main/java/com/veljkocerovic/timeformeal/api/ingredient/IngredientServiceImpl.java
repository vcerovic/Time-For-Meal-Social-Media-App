package com.veljkocerovic.timeformeal.api.ingredient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IngredientServiceImpl implements IngredientService{

    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    public List<Ingredient> getIngredientsByIds(List<Integer> ids) {
        List<Ingredient> ingredients = new ArrayList<>();

        for(Integer id : ids){
            Optional<Ingredient> optional = ingredientRepository.findById(id);
            optional.ifPresent(ingredients::add);
        }

        return ingredients;
    }

    @Override
    public List<Ingredient> getAllIngredientsByLetter(String prefix) {
        return ingredientRepository.findByNameStartingWith(prefix);
    }
}
