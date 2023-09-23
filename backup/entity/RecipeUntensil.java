package com.example.swp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "RecipeUntensil")
public class RecipeUntensil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeUntensilId;

    // Getters and setters

    @ManyToOne
    @JoinColumn(name = "recipeUtensilRecipe")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "recipeUtensilUtensil")
    private Utensils utensil;

    // Constructors
}
