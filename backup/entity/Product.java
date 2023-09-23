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
@Table(name = "Product")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private double price;
    private short unitsInStock;
    private String origin;
    private String productDetail;

    // Getters and setters

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private CategoryProduct category;

    @OneToMany(mappedBy = "product")
    private List<ProductImage> productImages;

    @OneToMany(mappedBy = "product")
    private List<ProductNutrition> productNutritions;

    @OneToMany(mappedBy = "product")
    private List<CartItems> cartItems;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;

    // @ManyToMany(mappedBy = "products")
    // private List<Recipe> recipes;

    // Constructors
}
