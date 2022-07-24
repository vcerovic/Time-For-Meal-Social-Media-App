package com.veljkocerovic.timeformeal.recipe;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "recipes")
@Data
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private int id;

    private String name;
    private String instruction;

    @Column(name = "prep_time")
    private int prepTime;

    @Column(name = "cook_time")
    private int cookTime;

    private int serving;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;



}
