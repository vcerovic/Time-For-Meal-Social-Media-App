package com.veljkocerovic.timeformeal.api.recipe;

import com.veljkocerovic.timeformeal.api.recipe.category.RecipeCategory;
import com.veljkocerovic.timeformeal.api.recipe.comment.RecipeComment;
import com.veljkocerovic.timeformeal.api.recipe.ingredient.Ingredient;
import com.veljkocerovic.timeformeal.api.recipe.rating.RecipeRating;
import com.veljkocerovic.timeformeal.api.user.appuser.AppUser;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "recipes")
@Data
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Integer id;

    //Reference to owner (user)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private AppUser owner;

    //Basic info
    private String name;
    private String instruction;

    @Column(name = "prep_time")
    private int prepTime;

    @Column(name = "cook_time")
    private int cookTime;

    private int serving;

    private String image;

    //Recipe category
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private RecipeCategory recipeCategory;


    //Recipe ingredients
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "recipe_ingredients",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private Set<Ingredient> ingredients;


    //Reference to users that liked this recipe
    @ManyToMany(mappedBy = "recipesLikes")
    private Set<AppUser> usersLikes;


    //Recipe ratings
    @OneToMany(mappedBy = "recipe")
    Set<RecipeRating> recipeRatings;


    //Recipe comments
    @OneToMany(mappedBy = "recipe")
    Set<RecipeComment> recipeComments;

}
