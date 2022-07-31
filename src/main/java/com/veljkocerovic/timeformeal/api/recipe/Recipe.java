package com.veljkocerovic.timeformeal.api.recipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.veljkocerovic.timeformeal.api.category.RecipeCategory;
import com.veljkocerovic.timeformeal.api.comment.RecipeComment;
import com.veljkocerovic.timeformeal.api.ingredient.Ingredient;
import com.veljkocerovic.timeformeal.api.rating.RecipeRating;
import com.veljkocerovic.timeformeal.api.user.AppUser;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

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
    @JoinColumn(
            name="user_id",
            foreignKey=@ForeignKey(name = "FK_USER_ID"))
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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name="category_id",
            foreignKey=@ForeignKey(name = "FK_RECIPE_CATEGORY"))
    private RecipeCategory recipeCategory;


    //Recipe ingredients
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "recipe_ingredients",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<Ingredient> ingredients;


    //Reference to users that liked this recipe
    @ManyToMany(mappedBy = "recipesLikes", cascade = CascadeType.ALL)
    private List<AppUser> usersLikes;


    //Recipe ratings
    @OneToMany(mappedBy = "recipe")
    List<RecipeRating> recipeRatings;


    //Recipe comments
    @OneToMany(mappedBy = "recipe")
    List<RecipeComment> recipeComments;


}
