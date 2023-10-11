package com.ecommerce.library.service;

import com.ecommerce.library.model.ImgStep;
import org.springframework.stereotype.Service;

@Service
public interface ImgStepService {
    ImgStep saveImgStep(ImgStep imgStep);

    ImgStep getImgStepById(Long imgStepId);

    void deleteImgStep(Long imgStepId);
}
