package com.ecommerce.library.service;

import com.ecommerce.library.model.Recipe;
import com.ecommerce.library.model.Step;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StepService {

    Step saveStep(Step step);

    Step getStepById(Long stepId);

    void deleteStep(Long stepId);

    List<Step> getStepsByRecipeId(Long recipeId);

}
