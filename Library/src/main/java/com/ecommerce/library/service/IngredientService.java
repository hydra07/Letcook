package com.ecommerce.library.service;

import com.ecommerce.library.dto.IngredientDto;
import com.ecommerce.library.model.Ingredient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface IngredientService {
    List<Ingredient> dtosToIngredients(List<IngredientDto> ingredientDto);

    List<IngredientDto> ingredientsToDtos(List<Ingredient> ingredient);

    Map<String, Double> getNutrition(Ingredient ingredient);
}
