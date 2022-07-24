package com.veljkocerovic.timeformeal.recipe;

import com.veljkocerovic.timeformeal.recipe.ingredient.Ingredient;
import com.veljkocerovic.timeformeal.recipe.ingredient.IngredientCategory;
import com.veljkocerovic.timeformeal.user.User;
import com.veljkocerovic.timeformeal.user.UserRepository;
import com.veljkocerovic.timeformeal.user.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Rollback
class RecipeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        //Owner setup
        User testUser = new User();
        testUser.setUsername("Veljko");
        testUser.setEmail("veljko@gmail.com");
        testUser.setPassword("veljko123");
        testUser.setImage("veljko_image.png");
        testUser.setEnabled(true);
        testUser.setUserRole(UserRole.ADMIN);

        //Recipe setup
        Recipe recipe = new Recipe();
        recipe.setOwner(testUser);
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

        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(ingredient);
        recipe.setIngredients(ingredients);

        //Save recipe
        entityManager.persist(recipe);
        entityManager.flush();
    }

    @Test
    void findByName() {
        Optional<Recipe> foundRecipe = recipeRepository.findByName("Chicken with eggs");
        Recipe recipe = foundRecipe.orElseThrow(RuntimeException::new);

        Assertions.assertThat(recipe.getName()).isEqualTo("Chicken with eggs");
    }
}