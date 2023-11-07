package com.ecommerce.library.service;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.dto.RecipeDto;
import com.ecommerce.library.model.Recipe;
import net.minidev.json.JSONArray;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface RecipeService {


    Recipe save(Recipe recipe);

    List<Recipe> findAll();

    List<Recipe> findAllByConfirmed();

    Page<RecipeDto> pageRecipes(int pageNo);

    RecipeDto getById(Long id);

    Recipe getRecipeById(Long id);

    Recipe update(RecipeDto recipeDto);

    Recipe update(Recipe recipe);
    Recipe reject(RecipeDto recipeDto);

    Recipe reject(Recipe recipe);

    List<Recipe> searchRecipes(String keyword);

    JSONArray getAllRecipesJson();

    JSONArray getSuggestRecipes(String query);

    List<Recipe> findAllByCustomer(String username);

    int numOfUncheckedRecipe();

    int numOfRecipeToday();

    Long[] numOfRecipeByMonths();

}
