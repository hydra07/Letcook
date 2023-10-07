package com.ecommerce.library.model;

import jakarta.persistence.*;

public class NutritionProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nuttrition_product_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id" , referencedColumnName = "product_id")
    private Product product;

    @Column(columnDefinition = "VARCHAR(255)")
    private String imgPath;
}
