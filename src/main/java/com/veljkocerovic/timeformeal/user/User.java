package com.veljkocerovic.timeformeal.user;

import com.veljkocerovic.timeformeal.recipe.Recipe;
import com.veljkocerovic.timeformeal.recipe.RecipeRating;
import lombok.Data;

import javax.management.relation.Role;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    //Basic info
    private String username;
    private String email;
    private String password;
    private String image;

    //Roles that user has
    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


    //Recipes that user published
    @OneToMany(mappedBy = "owner")
    private Set<Recipe> recipes;


    //Recipes that user liked
    @ManyToMany
    @JoinTable(
            name = "recipe_likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id"))
    private Set<Recipe> recipesLikes;


    //Recipe ratings
    @OneToMany(mappedBy = "user")
    Set<RecipeRating> recipeRatings;
}
