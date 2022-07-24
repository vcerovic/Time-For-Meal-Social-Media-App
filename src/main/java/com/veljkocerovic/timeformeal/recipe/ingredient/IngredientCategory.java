package com.veljkocerovic.timeformeal.recipe.ingredient;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "ingredient_category")
@Data
public class IngredientCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int id;

    private String name;
}
