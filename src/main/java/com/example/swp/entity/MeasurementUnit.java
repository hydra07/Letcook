package com.example.swp.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Table(name = "MeasurementUnit")
@Data
public class MeasurementUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long measurementUnitId;
    private String measurementUnitName;
    private String measurementUnitSymbol;
    private String measurementUnitClass;

    // Getters and setters

    @OneToMany(mappedBy = "measurementUnit")
    private List<ProductNutrition> productNutritions;

    // Constructors
}
