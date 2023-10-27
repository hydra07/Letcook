package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.IngredientDto;
import com.ecommerce.library.model.Ingredient;
import com.ecommerce.library.repository.IngredientRepository;
import com.ecommerce.library.repository.ProductRepository;
import com.ecommerce.library.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private IngredientRepository ingredientRepository;
    public List<Ingredient> dtosToIngredients(List<IngredientDto> ingredientDtos) {
        List<Ingredient> ingredients = new ArrayList<>();
        for (IngredientDto ingredientDto : ingredientDtos) {
            Ingredient ingredient = new Ingredient();
            ingredient.setId(ingredientDto.getId());
            ingredient.setAmount(ingredientDto.getAmount());
            ingredient.setName(ingredientDto.getName());
            ingredient.setProduct(productRepository.getById(ingredientDto.getProductId()));
            ingredient.setRecipe(ingredientDto.getRecipe());
            ingredient.setMeasurement(ingredientDto.getMeasurement());
            ingredients.add(ingredient);
        }
        return ingredients;
    }

    public List<IngredientDto> ingredientsToDtos(List<Ingredient> ingredients) {
        List<IngredientDto> ingredientDtos = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            IngredientDto ingredientDto = new IngredientDto();
            ingredientDto.setId(ingredient.getId());
            ingredientDto.setAmount(ingredient.getAmount());
            ingredientDto.setName(ingredient.getName());
            ingredientDto.setProductId(ingredient.getProduct().getId());
            ingredientDto.setRecipe(ingredient.getRecipe());
            ingredientDto.setMeasurement(ingredient.getMeasurement());
            ingredientDtos.add(ingredientDto);
        }
        return ingredientDtos;
    }

    @Override
    public Map<String, Double> getNutrition(Ingredient ingredient) {
        Map<String, Double> nutritions = new HashMap<>();

        double change = 1;
        switch (ingredient.getMeasurement().getName()) {
            case "kilogram":
                change = 1000;
                break;
            case "tsp":
                change = 5;
                break;
            case "tbsp":
                change = 15;
                break;
            case "quả/củ/cái":
                change = ingredient.getProduct().getAverageWeight();
                break;
            case "ml":
                change = 1;
                break;
            case "lit":
                change = 1000;
                break;
            default:
                change = 1;
        }
        double portion = ingredient.getRecipe().getPortion();
        double amount = ingredient.getAmount() ;
        double calories = ingredient.getProduct().getCalories()/100 * amount * change / portion;
        double sugar = ingredient.getProduct().getSugar()/100 * amount * change / portion;
        double fat = ingredient.getProduct().getFat()/100 * amount * change / portion;
        double sodium = ingredient.getProduct().getSodium()/100 * amount * change / portion;
        double carbs = ingredient.getProduct().getCarbs()/100 * amount * change / portion;
        double fiber = ingredient.getProduct().getFiber()/100 * amount * change / portion;

        nutritions.put("calories", calories);
        nutritions.put("sugar", sugar);
        nutritions.put("fat", fat);
        nutritions.put("sodium", sodium);
        nutritions.put("carbs", carbs);
        nutritions.put("fiber", fiber);

        return nutritions;
    }


}
