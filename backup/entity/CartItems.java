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
@Table(name = "CartItems")
@Data
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;
    private short quantity;

    // Getters and setters

    @ManyToOne
    @JoinColumn(name = "cartId")
    private Carts cart;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    // Constructors
}
