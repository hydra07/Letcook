package com.ecommerce.customer.controller;

import com.ecommerce.library.model.*;
import com.ecommerce.library.service.MeasurementService;
import com.ecommerce.library.service.RecipeService;
import com.ecommerce.library.utils.ImageUpload;
import org.hibernate.id.IncrementGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RecipeController {

    @Autowired
    private RecipeService recipeService;


    @Autowired
    private MeasurementService measurementService;

    @GetMapping("/recipe-form")
    public String recipeForm() {
        return "recipe-form";
    }

    @PostMapping("/add-recipe")
    public String addRecipe(MultipartHttpServletRequest request){

        //recipe
        Recipe recipe = new Recipe();
        List<Ingredient> ingredients = new ArrayList<>();
        List<Step> steps = new ArrayList<>();

        //get data from form
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
<<<<<<< HEAD
            System.out.println(request.getParameter("ingredient" + i));
            System.out.println(request.getParameter("amount" + i));
            System.out.println(request.getParameter("measurements" + i));
=======
            System.out.println(request.getParameter("ingredient" + i) +" "+ request.getParameter("measurements" + i) +" "+ request.getParameter("amount" + i));
            Ingredient ingredient = new Ingredient();
            ingredient.setName(request.getParameter("ingredient" + i));
            ingredient.setAmount(Double.parseDouble(request.getParameter("amount" + i)));
            ingredient.setMeasurement(measurementService.findByName(request.getParameter("measurements" + i)));
            ingredient.setRecipe(recipe);
            ingredients.add(ingredient);
>>>>>>> 88e7c4e14f699c1f53dc1bfe9abc1f2b3fb0a4e8
        }

        for(int i = 1; i <= stepCount; i++) {
            ImageUpload imageUpload = new ImageUpload();
            System.out.println(request.getParameter("step" + i));
            List<MultipartFile> images = request.getFiles("step" + i + "Image");

            List<ImgStep> imgSteps = new ArrayList<>();
            Step step = new Step();
            step.setDescription(request.getParameter("step" + i));
            step.setRecipe(recipe);


            for (MultipartFile image : images) {
                System.out.println(image.getOriginalFilename());
                ImgStep imgStep = new ImgStep();
                imgStep.setImgPath(imageUpload.getURL(imageUpload.uploadImage(image,"/recipe/"),"recipe"));;
                imgStep.setStep(step);
                imgSteps.add(imgStep);
            }
            step.setImages(imgSteps);
            steps.add(step);

        }

        try {
            recipe.setName(recipeName);
            recipe.setDescription(recipeDescription);
            recipe.setCookingTime(cookingTime);
            recipe.setPortion(portion);
            recipe.setIngredients(ingredients);
            recipe.setSteps(steps);
            recipeService.save(recipe);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return "index";
    }


}
