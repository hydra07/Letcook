package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.Recipe;
import com.ecommerce.library.repository.IngredientRepository;
import com.ecommerce.library.repository.RecipeRepository;
import com.ecommerce.library.repository.StepRepository;
import com.ecommerce.library.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

}