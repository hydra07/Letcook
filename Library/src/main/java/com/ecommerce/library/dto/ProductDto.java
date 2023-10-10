package com.ecommerce.library.dto;

import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Measurement;
import com.ecommerce.library.model.Nutrition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private double costPrice;
    private double salePrice;
    private double averageWeight;
    private int currentQuantity;
    private Category category;
    private Measurement measurement;
    private List<String> imgProducts;
    private boolean activated;
    private boolean deleted;
    private double amountToSell;
    private double calories;
    private double sugar;
    private double fat;
    private double sodium;
    private double carbs;
    private double fiber;
}