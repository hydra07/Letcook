package com.example.swp.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "RecipeStep")
@Data
public class RecipeStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeStepId;
    private String recipeDescription;
    private String recipeImage;

    // Getters and setters

    @ManyToOne
    @JoinColumn(name = "recipeId")
    private Recipe recipe;

    @OneToMany(mappedBy = "recipeStep")
    private List<RecipeStepImg> recipeStepImgs;

    // Constructors
}

