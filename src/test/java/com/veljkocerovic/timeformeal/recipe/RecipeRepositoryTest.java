package com.veljkocerovic.timeformeal.recipe;

import com.veljkocerovic.timeformeal.api.recipe.Recipe;
import com.veljkocerovic.timeformeal.api.recipe.RecipeRepository;
import com.veljkocerovic.timeformeal.api.category.RecipeCategory;
import com.veljkocerovic.timeformeal.api.ingredient.Ingredient;
import com.veljkocerovic.timeformeal.api.ingredient.IngredientCategory;
import com.veljkocerovic.timeformeal.api.user.AppUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.*;

@DataJpaTest
class RecipeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        //Owner setup
        AppUser testAppUser = new AppUser();
        testAppUser.setUsername("Veljko");
        testAppUser.setEmail("veljko@gmail.com");
        testAppUser.setPassword("veljko123");
        testAppUser.setImage("veljko_image.png");
        testAppUser.setEnabled(true);

        //Recipe setup
        Recipe recipe = new Recipe();
        recipe.setOwner(testAppUser);
        recipe.setName("Chicken with eggs");
        recipe.setInstruction("Dummy instructions");
        recipe.setPrepTime(2);
        recipe.setCookTime(45);
        recipe.setServing(4);

        //Recipe category
        RecipeCategory recipeCategory = new RecipeCategory();
        recipeCategory.setName("Breakfast");
        recipe.setRecipeCategory(recipeCategory);

        //Recipe ingredients
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Egg");

        IngredientCategory eggsCategory = new IngredientCategory();
        eggsCategory.setName("Dairy & Eggs");
        ingredient.setCategory(eggsCategory);

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient);
        recipe.setIngredients(ingredients);

        //Save recipe
        entityManager.persist(recipe);
        entityManager.flush();
    }

    @Test
    void testFindByName() {
        Optional<Recipe> foundRecipe = recipeRepository.findByName("Chicken with eggs");
        Recipe recipe = foundRecipe.orElseThrow(RuntimeException::new);

        Assertions.assertThat(recipe.getName()).isEqualTo("Chicken with eggs");
    }

    @Test
    void testRecipeRelationships(){
        Optional<Recipe> foundRecipe = recipeRepository.findByName("Chicken with eggs");
        Recipe recipe = foundRecipe.orElseThrow(RuntimeException::new);

        //Check owner
        Assertions.assertThat(recipe.getOwner().getUsername()).isEqualTo("Veljko");

        //Check ingredients size
        Assertions.assertThat(recipe.getIngredients().size()).isGreaterThan(0);

        //Check first ingredient
        Optional<Ingredient> first = recipe.getIngredients().stream().findFirst();
        Ingredient ingredient = first.orElseThrow(RuntimeException::new);
        Assertions.assertThat(ingredient.getName()).isEqualTo("Egg");

        //Check category
        Assertions.assertThat(recipe.getRecipeCategory().getName()).isEqualTo("Breakfast");
    }
}