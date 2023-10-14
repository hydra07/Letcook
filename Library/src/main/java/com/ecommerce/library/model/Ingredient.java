package com.ecommerce.library.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Long id;

    private String name;

    private Double amount;

    @OneToOne
    @JoinColumn(name="measurement_id")
    private Measurement measurement;


    @ManyToOne
    @JoinColumn(name="recipe_id")
    private Recipe recipe;


}
