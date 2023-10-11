package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.ImgStep;
import com.ecommerce.library.repository.ImgStepRepository;
import com.ecommerce.library.repository.RecipeRepository;
import com.ecommerce.library.repository.StepRepository;
import com.ecommerce.library.service.ImgStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImgStepServiceImpl implements ImgStepService {
    @Autowired
    private ImgStepRepository imgStepRepository;

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public ImgStep saveImgStep(ImgStep imgStep) {
        return null;
    }

    @Override
    public ImgStep getImgStepById(Long imgStepId) {
        return null;
    }

    @Override
    public void deleteImgStep(Long imgStepId) {

    }
}
