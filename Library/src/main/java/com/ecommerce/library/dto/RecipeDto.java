package com.ecommerce.library.dto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Ingredient;
import com.ecommerce.library.model.Step;
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
    private Long id;
    private String recipeName;
    private String recipeDescription;
    private int portion;
    private int cookingTime;

    private List<Ingredient> ingredients;
    private List<Step> steps;
    private boolean confirmed;
    private Customer customer;
    private boolean checked;
    private String image;
}

