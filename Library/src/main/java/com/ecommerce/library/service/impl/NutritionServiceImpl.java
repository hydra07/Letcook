package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.Measurement;
import com.ecommerce.library.model.Nutrition;
import com.ecommerce.library.repository.MeasurementRepository;
import com.ecommerce.library.repository.NutritionRepository;
import com.ecommerce.library.service.NutritionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NutritionServiceImpl implements NutritionService {
//    @Autowired
//    private NutritionRepository nutritionRepository;
//    @Override
//    public List<Nutrition> findAll() {
//        return nutritionRepository.findAll();
//    }
//
//    @Override
//    public Nutrition save(Nutrition nutrition) {
//        Nutrition nutritionSave = new Nutrition(nutrition.getName());
//        return nutritionRepository.save(nutrition);
//    }
//
//    @Override
//    public Optional<Nutrition> findById(long id) {
//        return nutritionRepository.findById(id);
//    }
//
//    @Override
//    public Nutrition update(Nutrition nutrition) {
//        Nutrition nutritionUpdate = null;
//        try {
//            nutritionUpdate = nutritionRepository.findById(nutrition.getId()).get();
//            nutritionUpdate.setName(nutrition.getName());
//            nutritionUpdate.set_deleted(nutrition.is_deleted());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return nutritionRepository.save(nutritionUpdate);
//    }
//
//    @Override
//    public void deleteById(long id) {
//        Nutrition nutrition = nutritionRepository.getById(id);
//        nutrition.set_deleted(true);
//        nutritionRepository.save(nutrition);
//    }
//
//    @Override
//    public void enableById(long id) {
//        Nutrition nutrition = nutritionRepository.getById(id);
//        nutrition.set_deleted(false);
//        nutritionRepository.save(nutrition);
//    }
//
//    @Override
//    public List<Nutrition> findAllByActivated() {
//        return nutritionRepository.findAllByActivated();
//    }
}
