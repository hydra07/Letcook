package com.ecommerce.library.dto;

import lombok.Data;

@Data
public class CategoryCount {
    private Long id;
    private String categoryName;
    private Long numberOfProduct;

    public CategoryCount(Long id, String categoryName, Long numberOfProduct) {
        this.id = id;
        this.categoryName = categoryName;
        this.numberOfProduct = numberOfProduct;
    }
}
