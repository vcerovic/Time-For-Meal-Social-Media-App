package com.veljkocerovic.timeformeal.api.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.veljkocerovic.timeformeal.api.recipe.Recipe;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "recipe_category")
@Data
@NoArgsConstructor
public class RecipeCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int id;

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "recipeCategory")
    private List<Recipe> recipes;


}
