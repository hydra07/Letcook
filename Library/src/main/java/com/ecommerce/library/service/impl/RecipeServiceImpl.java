package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.dto.RecipeDto;
import com.ecommerce.library.model.Ingredient;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.model.Recipe;
import com.ecommerce.library.repository.IngredientRepository;
import com.ecommerce.library.repository.RecipeRepository;
import com.ecommerce.library.repository.StepRepository;
import com.ecommerce.library.service.IngredientService;
import com.ecommerce.library.service.RecipeService;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    IngredientService ingredientService;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    public List<Recipe> findAllByConfirmed() {
        return recipeRepository.getRecipeByConfirmed();
    }

    @Override
    public Page<RecipeDto> pageRecipes(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<RecipeDto> recipes = transfer(recipeRepository.findAll());
        Page<RecipeDto> recipePages = toPage(recipes, pageable);
        return recipePages;
    }

    @Override
    public RecipeDto getById(Long id) {
        Recipe recipe = recipeRepository.getById(id);
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(recipe.getId());
        recipeDto.setRecipeName(recipe.getName());
        recipeDto.setRecipeDescription(recipe.getDescription());
        recipeDto.setConfirmed(recipe.is_confirmed());
        recipeDto.setPortion(recipe.getPortion());
        recipeDto.setCookingTime(recipe.getCookingTime());
        recipeDto.setIngredients(recipe.getIngredients());
        recipeDto.setSteps(recipe.getSteps());
        recipeDto.setCustomer(recipe.getCustomer());
        recipeDto.setChecked(recipe.is_checked());
        return recipeDto;
    }

    @Override
    public Recipe getRecipeById(Long id) {
        return recipeRepository.getById(id);
    }

    @Override
    public Recipe update(RecipeDto recipeDto) {
        Recipe recipe = recipeRepository.getById(recipeDto.getId());
        recipe.setName(recipeDto.getRecipeName());
        recipe.setDescription(recipeDto.getRecipeDescription());
        recipe.setPortion(recipeDto.getPortion());
        recipe.set_confirmed(true);
        recipe.setCookingTime(recipeDto.getCookingTime());
//        recipe.setIngredients(recipeDto.getIngredients());
        for (int i = 0; i < recipeDto.getIngredients().size(); i++) {
            recipe.getIngredients().get(i).setProduct(recipeDto.getIngredients().get(i).getProduct());
        }
        recipe.setSteps(recipeDto.getSteps());
//        recipe.setCustomer(recipeDto.getCustomer());
        recipe.set_checked(true);
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe reject(RecipeDto recipeDto) {
        Recipe recipe = recipeRepository.getById(recipeDto.getId());
        recipe.setName(recipeDto.getRecipeName());
        recipe.setDescription(recipeDto.getRecipeDescription());
        recipe.setPortion(recipeDto.getPortion());
        recipe.set_confirmed(false);
        recipe.setCookingTime(recipeDto.getCookingTime());
        recipe.setIngredients(recipeDto.getIngredients());
        recipe.setSteps(recipeDto.getSteps());
        recipe.setCustomer(recipeDto.getCustomer());
        recipe.set_checked(true);
        return recipeRepository.save(recipe);
    }

    private Page toPage(List<RecipeDto> list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ?
                list.size() : pageable.getOffset() + pageable.getPageSize());

        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

    private List<RecipeDto> transfer(List<Recipe> recipes) {
        List<RecipeDto> recipeDtoList = new ArrayList<>();
        for (Recipe recipe : recipes) {
            RecipeDto recipeDto = new RecipeDto();
            recipeDto.setId(recipe.getId());
            recipeDto.setRecipeName(recipe.getName());
            recipeDto.setRecipeDescription(recipe.getDescription());
            recipeDto.setConfirmed(recipe.is_confirmed());
            recipeDto.setPortion(recipe.getPortion());
            recipeDto.setCookingTime(recipe.getCookingTime());
            recipeDto.setIngredients(recipe.getIngredients());
            recipeDto.setSteps(recipe.getSteps());
            recipeDto.setCustomer(recipe.getCustomer());
            recipeDto.setChecked(recipe.is_checked());
            recipeDtoList.add(recipeDto);
        }
        return recipeDtoList;
    }

    @Override
    public List<Recipe> searchRecipes(String keyword) {
        return recipeRepository.getRecipeByKeyword(keyword);
    }

    @Override
    public JSONArray getAllRecipesJson() {
        List<Recipe> recipes = recipeRepository.findAll();
        JSONArray jsonArray = new JSONArray();
        for (Recipe recipe : recipes) {
            jsonArray.add(recipe.getName());
        }
        return jsonArray;
    }

    @Override
    public JSONArray getSuggestRecipes(String query) {
        List<Recipe> recipes = recipeRepository.getRecipeByKeyword(query);
        JSONArray jsonArray = new JSONArray();
        for (Recipe recipe : recipes) {
            jsonArray.add(recipe.getName());
        }
        return jsonArray;
    }


}