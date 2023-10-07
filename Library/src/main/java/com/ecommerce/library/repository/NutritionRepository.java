package com.ecommerce.library.repository;

import com.ecommerce.library.model.Measurement;
import com.ecommerce.library.model.Nutrition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NutritionRepository{
//    @Query("select n from Nutrition n where n.is_deleted = false")
//    List<Nutrition> findAllByActivated();
}
