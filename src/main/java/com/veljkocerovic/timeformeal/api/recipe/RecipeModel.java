package com.veljkocerovic.timeformeal.api.recipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ControllerAdvice
public class RecipeModel {

    @NotEmpty
    @Size(min = 4, max = 50, message = "Name must be between 4 and 50 characters")
    private String name;

    @NotEmpty
    @Size(min = 40, message = "Instructions must have at least 40 characters")
    private String instruction;

    @NotNull
    @Min(value = 1, message = "Preparation time should be at least 1 minute")
    private int prepTime;

    @NotNull
    @Min(value = 1, message = "Cook time should be at least 1 minute")
    private int cookTime;

    @NotNull
    @Min(value = 1, message = "Serving should be at least for 1 person")
    private int serving;

    @NotNull(message = "You need to upload an image")
    private MultipartFile image;

    @NotNull
    @Min(value = 1, message = "You need to select valid recipe category")
    private Integer recipeCategoryId;

    @NotNull(message = "You need to select ingredients for your recipe")
    private List<Integer> ingredientsIds;

}
