package com.veljkocerovic.timeformeal.api.ingredient;

import java.util.List;

public interface IngredientService {

    List<Ingredient> getIngredientsByIds(List<Integer> ids);
}
