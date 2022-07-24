package com.veljkocerovic.timeformeal.recipe.category;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "recipe_category")
@Data
public class RecipeCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int id;

    private String name;
}