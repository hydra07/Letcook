package com.example.swp.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Recipe")
@Data
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;
    private String recipeName;
    private String recipeDescription;
    private int recipeExcellent;
    private int recipeLove;
    private int recipeBad;
    private boolean isAccepted;

    // Getters and setters

    @ManyToOne
    @JoinColumn(name = "recipeLevelId")
    private RecipeLevel recipeLevel;

    @ManyToOne
    @JoinColumn(name = "recipeAuthor")
    private Users recipeAuthor;

    @ManyToMany
    @JoinTable(
        name = "RecipeCategory",
        joinColumns = @JoinColumn(name = "recipeId"),
        inverseJoinColumns = @JoinColumn(name = "dishCategoryId")
    )
    private List<DishCategory> dishCategories;

    @OneToMany(mappedBy = "recipe")
    private List<RecipeUntensil> recipeUntensils;

    @OneToMany(mappedBy = "recipe")
    private List<RecipeStep> recipeSteps;

    @OneToMany(mappedBy = "recipe")
    private List<RecipeComment> recipeComments;

    @OneToMany(mappedBy = "recipe")
    private List<RecipeProduct> recipeProducts;

    @OneToMany(mappedBy = "recipe")
    private List<SavedRecipe> savedRecipes;

    @OneToMany(mappedBy = "recipe")
    private List<Cooksnap> cooksnaps;

    // Constructors
}