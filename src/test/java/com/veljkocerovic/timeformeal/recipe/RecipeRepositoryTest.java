package com.veljkocerovic.timeformeal.recipe;

import com.veljkocerovic.timeformeal.recipe.category.RecipeCategory;
import com.veljkocerovic.timeformeal.recipe.ingredient.Ingredient;
import com.veljkocerovic.timeformeal.recipe.ingredient.IngredientCategory;
import com.veljkocerovic.timeformeal.user.model.User;
import com.veljkocerovic.timeformeal.user.model.UserRole;
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