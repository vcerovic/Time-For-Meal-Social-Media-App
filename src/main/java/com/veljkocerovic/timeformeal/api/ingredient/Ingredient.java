package com.veljkocerovic.timeformeal.api.ingredient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.veljkocerovic.timeformeal.api.recipe.Recipe;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ingredients")
@Data
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Integer id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private IngredientCategory category;

    @JsonIgnore
    @ManyToMany(mappedBy = "ingredients")
    private Set<Recipe> recipes;
}
