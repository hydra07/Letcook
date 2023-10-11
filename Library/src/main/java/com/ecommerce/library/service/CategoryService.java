package com.ecommerce.library.service;


import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface CategoryService {
    List<Category> findAll();
    Category save(Category category);
    Optional<Category> findById(long id);
    Category update(Category category);
    void deleteById(long id);
    void enableById(long id);
    List<Category> findAllByActivated();
    //CUSTOMER
    List<CategoryDto> getCategoryAndProduct();
}
