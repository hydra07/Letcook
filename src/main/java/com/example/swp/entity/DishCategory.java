package com.example.swp.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "DishCategory")
@Data
public class DishCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dishCategoryId;
    private String dishCategoryName;

    // Getters and setters

    @ManyToMany(mappedBy = "dishCategories")
    private List<Recipe> recipes;

    // Constructors
}
