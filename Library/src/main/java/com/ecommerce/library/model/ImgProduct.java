package com.ecommerce.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "img_products")
public class ImgProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "img_product_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id" , referencedColumnName = "product_id")
    private Product product;

    @Column(columnDefinition = "VARCHAR(255)")
    private String imgPath;
}
