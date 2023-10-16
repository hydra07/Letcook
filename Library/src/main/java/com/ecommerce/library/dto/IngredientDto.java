package com.ecommerce.library.dto;

import com.ecommerce.library.model.Measurement;
import com.ecommerce.library.model.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDto {
    private Long id;
    private String name;
    private double amount;
    private Measurement measurement;
    private Long productId;
    private Recipe recipe;
}
