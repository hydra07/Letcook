package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.Recipe;
import com.ecommerce.library.model.Step;
import com.ecommerce.library.repository.ImgStepRepository;
import com.ecommerce.library.repository.RecipeRepository;
import com.ecommerce.library.repository.StepRepository;
import com.ecommerce.library.service.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StepServiceImpl implements StepService {

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private ImgStepRepository imgStepRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public Step saveStep(Step step) {
        return stepRepository.save(step);
    }

    @Override
    public Step getStepById(Long stepId) {
        return stepRepository.findById(stepId).orElseThrow(() -> new RuntimeException("Step not found"));
    }

    @Override
    public void deleteStep(Long stepId) {
        Step step = getStepById(stepId);
        stepRepository.delete(step);
    }

    @Override
    public List<Step> getStepsByRecipeId(Long recipeId) {
//        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RuntimeException("Recipe not found"));
//        return stepRepository.findAllByRecipe(recipe);
        return null;
    }


}
