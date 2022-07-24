package com.veljkocerovic.timeformeal.ingredient;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "ingredients")
@Data
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private int id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private IngredientCategory category;

}
