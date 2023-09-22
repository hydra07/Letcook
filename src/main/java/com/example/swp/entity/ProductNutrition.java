package com.example.swp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ProductNutrition")
@Data
public class ProductNutrition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productNutritionId;
    private double quantity;

    // Getters and setters

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "nutritionId")
    private Nutrition nutrition;

    @ManyToOne
    @JoinColumn(name = "measurementUnitId")
    private MeasurementUnit measurementUnit;

    // Constructors
}

