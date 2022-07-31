package com.veljkocerovic.timeformeal.api.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.veljkocerovic.timeformeal.api.recipe.Recipe;
import com.veljkocerovic.timeformeal.api.user.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recipe_comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Recipe recipe;

    @Column(name = "time_created")
    private LocalDateTime createdAt;

    private String comment;
}
