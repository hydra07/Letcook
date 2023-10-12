package com.ecommerce.customer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@Controller
public class RecipeController {

    @GetMapping("/recipe-form")
    public String recipeForm() {
        return "recipe-form";
    }

    @PostMapping("/add-recipe")
    public String addRecipe(MultipartHttpServletRequest request){

        String recipeName = request.getParameter("recipeName");
        String recipeDescription = request.getParameter("recipeDescription");
        int portion = Integer.parseInt(request.getParameter("portion"));
        int cookingTime = Integer.parseInt(request.getParameter("cookingTime"));

        int ingredientCount = Integer.parseInt(request.getParameter("ingredientCount"));
        int stepCount = Integer.parseInt(request.getParameter("stepCount"));
        if(ingredientCount == 0 || stepCount == 0) {
            return "index";
        }
        System.out.println("songuyenlieu:" + ingredientCount);
        System.out.println("sobuoc:" + stepCount);

        for(int i = 1; i <= ingredientCount; i++) {
            System.out.println(request.getParameter("ingredient" + i));
        }

        for(int i = 1; i <= stepCount; i++) {
            System.out.println(request.getParameter("step" + i));
            List<MultipartFile> images = request.getFiles("step" + i + "Image");
            for (MultipartFile image : images) {
                System.out.println(image.getOriginalFilename());
            }
        }

        return "index";
    }


}
