package com.veljkocerovic.timeformeal.user;

import com.veljkocerovic.timeformeal.recipe.Recipe;
import com.veljkocerovic.timeformeal.recipe.comment.RecipeComment;
import com.veljkocerovic.timeformeal.recipe.rating.RecipeRating;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    //Basic info
    private String username;
    private String email;
    private String password;
    private String image;
    private boolean enabled = false;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;


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
    Set<RecipeRating> userRatings;

    //Recipe comments
    @OneToMany(mappedBy = "user")
    Set<RecipeComment> userComments;
}
