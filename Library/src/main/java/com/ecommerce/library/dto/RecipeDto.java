package com.ecommerce.library.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDto {
    // Các trường thông tin chung
    private String recipeName;
    private String recipeDescription;
    private int portion;
    private int cookingTime;

    private List<String> ingredients;
    private List<StepDto> steps;
}

