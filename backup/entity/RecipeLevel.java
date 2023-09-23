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
@Table(name = "RecipeLevel")
@Data
public class RecipeLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeLevelId;
    private String levelName;

    // Getters and setters

    @OneToMany(mappedBy = "recipeLevel")
    private List<Recipe> recipes;

    // Constructors
}
