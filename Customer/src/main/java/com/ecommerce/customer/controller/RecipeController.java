package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.RecipeDto;
import com.ecommerce.library.dto.StepDto;
import com.ecommerce.library.model.Recipe;
import com.ecommerce.library.repository.RecipeRepository;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

@Controller
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;
    private List<RecipeDto> recipes = new ArrayList<>();

    @GetMapping("/recipe")
    public String showRecipeForm(Model model) {
        model.addAttribute("recipes", recipes);
        model.addAttribute("recipe", new RecipeDto());
        return "recipe";
    }
//
//    @PostMapping("/add-recipe")
//    public String addRecipe(MultipartHttpServletRequest request, @ModelAttribute RecipeDto recipe) {
//        recipes.add(recipe);
//        return "redirect:/recipe";
//    }

    @PostMapping("/add-recipe")
    public String handleFormUpload(MultipartHttpServletRequest request) {
        String recipeName = request.getParameter("recipe");
        System.out.println("Tên công thức: " + recipeName);

        // Lấy danh sách nguyên liệu từ form
        String[] ingredients = request.getParameterValues("ingredient");
        for (String ingredient : ingredients) {
            System.out.println("Nguyên liệu: " + ingredient);
        }

        // Lấy danh sách bước nấu ăn và ảnh tương ứng
        String[] steps = request.getParameterValues("step");
        for (int i = 0; i < steps.length; i++) {
            System.out.println("Bước " + (i + 1) + ": " + steps[i]);

            // Lấy danh sách ảnh cho mỗi bước
            List<MultipartFile> images = request.getFiles("step" + (i+1) + "Images");
            for (MultipartFile image : images) {
                // Xử lý và lưu ảnh vào đâu đó trên máy chủ
                System.out.println(image.getOriginalFilename());
            }
        }

        // Trả về một view thành công
        return "redirect:/recipe";
    }



}
