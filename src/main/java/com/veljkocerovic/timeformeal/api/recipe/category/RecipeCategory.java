package com.veljkocerovic.timeformeal.api.recipe.category;

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

    @OneToMany(mappedBy = "recipeCategory")
    private List<Recipe> recipes;


}