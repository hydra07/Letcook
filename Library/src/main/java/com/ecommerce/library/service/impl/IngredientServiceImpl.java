package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.IngredientDto;
import com.ecommerce.library.model.Ingredient;
import com.ecommerce.library.repository.IngredientRepository;
import com.ecommerce.library.repository.ProductRepository;
import com.ecommerce.library.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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



}
