package com.ecommerce.library.service;

import com.ecommerce.library.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface RecipeService {

    Recipe save(Recipe recipe);

    List<Recipe> findAll();

}
