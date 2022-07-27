package com.veljkocerovic.timeformeal.api.recipe.ingredient;

import java.util.List;
import java.util.Set;

public interface IngredientService {

    List<Ingredient> getIngredientsByIds(List<Integer> ids);
}
