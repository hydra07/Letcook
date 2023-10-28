package com.ecommerce.library.repository;

import com.ecommerce.library.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query("SELECT r FROM Recipe r WHERE r.is_confirmed = true")
    List<Recipe> getRecipeByConfirmed();


    @Query(value = "SELECT DISTINCT r.* FROM recipes r " +
            "INNER JOIN ingredients i ON r.recipe_id = i.recipe_id " +
            "WHERE r.is_confirmed = 1 " +
            "AND (r.name LIKE %?1% OR r.description LIKE %?1% " +
            "OR i.name LIKE %?1%)",
            nativeQuery = true)
    List<Recipe> getRecipeByKeyword(String keyword);

    @Query(value = "SELECT COALESCE(COUNT(recipe_id),0) FROM recipes WHERE is_checked = 0", nativeQuery = true)
    int countByIsCheckedFalse();
    @Query(value = "SELECT COALESCE(COUNT(recipe_id), 0) FROM recipes WHERE CAST(create_at AS DATE) = CAST(GETDATE() AS DATE)", nativeQuery = true)
    int countTodayRecipe();

    @Query(value = "SELECT COALESCE(COUNT(recipes.recipe_id), 0) AS RecipeCount " +
            "FROM Months " +
            "LEFT JOIN recipes ON Months.MonthID = MONTH(recipes.create_at) AND YEAR(recipes.create_at) = YEAR(GETDATE()) AND recipes.is_confirmed = 1" +
            "GROUP BY Months.MonthID"
            , nativeQuery = true)
    List<Long> countRecipeByMonths();


}