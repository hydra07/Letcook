package com.example.swp.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Cooksnap")
@Data
public class Cooksnap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cooksnapId;
    private String cooksnapImage;
    private LocalDateTime cooksnapDate;

    // Getters and setters

    @ManyToOne
    @JoinColumn(name = "recipeId")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Users user;

    // Constructors
}
