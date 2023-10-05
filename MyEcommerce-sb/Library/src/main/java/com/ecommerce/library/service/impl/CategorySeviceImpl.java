package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.repository.CategoryRepository;
import com.ecommerce.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategorySeviceImpl implements CategoryService {
    @Autowired
    private CategoryRepository repo;
    @Override
    public List<Category> findAll() {
        return repo.findAll();
    }

    @Override
    public Category save(Category category) {
        Category categorySave = new Category(category.getName());
        return repo.save(categorySave);
    }

    @Override
    public Optional<Category> findById(long id) {
        return repo.findById(id);
    }

    @Override
    public Category update(Category category) {
        Category categoryUpdate = null;
        try {
            categoryUpdate= repo.findById(category.getId()).get();
            categoryUpdate.setName(category.getName());
            categoryUpdate.set_activated(category.is_activated());
            categoryUpdate.set_deleted(category.is_deleted());
        }catch (Exception e){
            e.printStackTrace();
        }
        return repo.save(categoryUpdate);
    }
    @Override
    public void deleteById(long id) {
        Category category = repo.getById(id);
        category.set_deleted(true);
        category.set_activated(false);
        repo.save(category);
    }

    @Override
    public void enableById(long id) {
        Category category = repo.getById(id);
        category.set_activated(true);
        category.set_deleted(false);
        repo.save(category);
    }

    @Override
    public List<Category> findAllByActivated() {
        return repo.findAllByActivated();
    }

    @Override
    public List<CategoryDto> getCategoryAndProduct() {
        List<Category> categories = repo.findAllByActivated();
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for(Category category: categories){
            CategoryDto categoryDto = new CategoryDto(category.getId(), category.getName(), repo.numberOfProductsByCategory(category.getId()));
            categoryDtoList.add(categoryDto);
        }
        return categoryDtoList;
    }
}
