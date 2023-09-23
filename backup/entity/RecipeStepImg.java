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
@Table(name = "RecipeStepImg")
@Data
public class RecipeStepImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeStepImgId;
    private String imageName;

    // Getters and setters

    @ManyToOne
    @JoinColumn(name = "recipeStepId")
    private RecipeStep recipeStep;

    // Constructors
}
