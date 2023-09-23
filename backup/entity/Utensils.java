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
@Table(name = "Utensil")
@Data
public class Utensils {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long utensilId;
    private String utensilName;

    // Getters and setters

    @ManyToMany(mappedBy = "utensils")
    private List<Recipe> recipes;

    // Constructors
}
