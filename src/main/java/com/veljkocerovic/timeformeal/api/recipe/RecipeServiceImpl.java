package com.veljkocerovic.timeformeal.api.recipe;

import com.veljkocerovic.timeformeal.api.category.RecipeCategoryService;
import com.veljkocerovic.timeformeal.api.ingredient.IngredientService;
import com.veljkocerovic.timeformeal.api.user.AppUser;
import com.veljkocerovic.timeformeal.api.user.AppUserService;
import com.veljkocerovic.timeformeal.exceptions.ImageSizeLimitException;
import com.veljkocerovic.timeformeal.exceptions.RecipeNotFoundException;
import com.veljkocerovic.timeformeal.exceptions.UserNotFoundException;
import com.veljkocerovic.timeformeal.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class RecipeServiceImpl implements RecipeService{

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private AppUserService appUserService;


    @Autowired
    private RecipeCategoryService recipeCategoryService;

    @Autowired
    private IngredientService ingredientService;


    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAllByOrderByIdAsc();
    }

    @Override
    public void saveRecipe(RecipeModel recipeModel, Authentication authentication)
            throws UserNotFoundException, ImageSizeLimitException {
        //Get current user
        AppUser currentUser = appUserService.findUserByUsername(authentication.getName());

        MultipartFile image = recipeModel.getImage();

        //Check if uploaded image is smaller than 5mb
        if (image.getSize() > 5e+6) {
            throw new ImageSizeLimitException("Only 5mb image size is allowed");
        }

        //Creating recipe image name
        String recipeImageName = recipeModel.getName().replaceAll(" ", "_").toLowerCase()
                + "_" + StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));

        //Saving recipe image
        try {
            FileUtil.saveFile(FileUtil.recipeImageDir, recipeImageName, image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Creating recipe form recipe model
        Recipe recipe = new Recipe();
        recipe.setImage(recipeImageName);
        recipe.setOwner(currentUser);

        swapModelToRecipe(recipeModel, recipe);

        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe);
        currentUser.setRecipes(recipes);

        //Save recipe to database
        recipeRepository.save(recipe);
    }


    @Override
    public Recipe getRecipeById(Integer recipeId) throws RecipeNotFoundException {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        return optionalRecipe.orElseThrow(() -> new RecipeNotFoundException("Recipe not found"));
    }

    @Override
    public void deleteRecipe(Integer recipeId) throws RecipeNotFoundException {
        Recipe recipe = getRecipeById(recipeId);

        recipeRepository.deleteRecipeById(recipeId);

        //Delete recipe image
        FileUtil.deleteFile(FileUtil.recipeImageDir + recipe.getImage());
    }

    @Override
    public void updateRecipe(Integer recipeId, RecipeModel newRecipeModel) throws RecipeNotFoundException, ImageSizeLimitException {
        Recipe oldRecipe = getRecipeById(recipeId);
        MultipartFile image = newRecipeModel.getImage();

        //Check if uploaded image is smaller than 5mb
        if (image.getSize() > 5e+6) {
            throw new ImageSizeLimitException("Only 5mb image size is allowed");
        }

        //Check if new passed image doesn't equal to old one
        if(!oldRecipe.getImage().equals(image.getName())){
            //Renaming image file
            String newRecipeImageName = newRecipeModel.getName().replaceAll(" ", "_").toLowerCase()
                    + "_" + StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));

            //Deleting old image
            FileUtil.deleteFile(FileUtil.recipeImageDir + oldRecipe.getImage());

            try {
                FileUtil.saveFile(FileUtil.recipeImageDir, newRecipeImageName, image);
            } catch (IOException e) {
                e.printStackTrace();
            }

            oldRecipe.setImage(newRecipeImageName);
        }

        //Change other properties
        swapModelToRecipe(newRecipeModel, oldRecipe);
        recipeRepository.save(oldRecipe);
    }

    private void swapModelToRecipe(RecipeModel recipeModel, Recipe recipe) {
        recipe.setName(recipeModel.getName());
        recipe.setInstruction(recipeModel.getInstruction());
        recipe.setPrepTime(recipeModel.getPrepTime());
        recipe.setCookTime(recipeModel.getCookTime());
        recipe.setServing(recipeModel.getServing());
        recipe.setRecipeCategory(recipeCategoryService.getRecipeCategoryById(recipeModel.getRecipeCategoryId()));
        recipe.setIngredients(ingredientService.getIngredientsByIds(recipeModel.getIngredientsIds()));
    }
}
