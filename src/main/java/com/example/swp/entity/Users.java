package com.example.swp.entity;

import java.util.List;

import javax.management.Notification;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Users")
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userName;
    private String userAvatar;
    private String userPhone;
    private String userAddress;
    private boolean isAdmin;

    // Getters and setters

    @OneToMany(mappedBy = "recipeAuthor")
    private List<Recipe> authoredRecipes;

    @OneToMany(mappedBy = "user")
    private List<SavedRecipe> savedRecipes;

    @OneToMany(mappedBy = "user")
    private List<RecipeComment> recipeComments;

    @OneToMany(mappedBy = "user")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;

    @OneToMany(mappedBy = "user")
    private List<Carts> carts;

    // Constructors
}