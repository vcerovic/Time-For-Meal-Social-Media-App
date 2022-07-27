package com.veljkocerovic.timeformeal.api.comment;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecipeCommentKey implements Serializable {

    @Column(name = "user_id")
    private int userId;

    @Column(name = "recipe_id")
    private int recipeId;
}