package com.veljkocerovic.timeformeal.api.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.veljkocerovic.timeformeal.api.comment.RecipeComment;
import com.veljkocerovic.timeformeal.api.rating.RecipeRating;
import com.veljkocerovic.timeformeal.api.recipe.Recipe;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    //Basic info
    @NotEmpty
    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
    private String username;

    @NotEmpty
    @Email
    @Size(min = 4, max = 70, message = "Email must be between 4 and 70 characters")
    private String email;

    @NotEmpty
    @Size(min = 4, max = 100, message = "Password must be between 4 and 50 characters")
    private String password;

    private String image;

    private boolean enabled = false;


    //Recipes that user published
    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private List<Recipe> recipes;


    //Recipes that user liked
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "recipe_likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id"))
    @JsonIgnore
    private List<Recipe> recipesLikes;


    //Recipe ratings
    @OneToMany(mappedBy = "appUser")
    @JsonIgnore
    Set<RecipeRating> userRatings;

    //Recipe comments
    @OneToMany(mappedBy = "appUser")
    @JsonIgnore
    Set<RecipeComment> userComments;

}
