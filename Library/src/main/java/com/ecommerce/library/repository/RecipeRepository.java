package com.ecommerce.library.repository;

import com.ecommerce.library.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query("SELECT r FROM Recipe r WHERE r.is_confirmed = true")
    List<Recipe> getRecipeByConfirmed();

    @Query("SELECT r FROM Recipe r WHERE r.is_confirmed = true AND r.name LIKE %?1% OR r.description LIKE %?1%")
    List<Recipe> getRecipeByKeyword(String keyword);

}