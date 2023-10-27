package com.ecommerce.customer.controller;

import com.ecommerce.library.model.*;
import com.ecommerce.library.service.CommentService;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.MeasurementService;
import com.ecommerce.library.service.RecipeService;
import com.ecommerce.library.utils.ImageUpload;
import org.hibernate.id.IncrementGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private MeasurementService measurementService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/find-recipe/{id}")
    public String recipeDetail(@PathVariable("id") Long id, Model model,Principal principal){
        Recipe recipe = recipeService.getRecipeById(id);
        List<Comment> comments =  commentService.findAllCommentByRecipeId(id);
        model.addAttribute("comments",comments);
        model.addAttribute("recipe",recipe);
        model.addAttribute("currentUser",customerService.findByUsername(principal.getName()));
        return "recipe-detail";
    }

    @GetMapping("/recipe-home")
    public String recipes(Model model){
        List<Recipe> recipes = recipeService.findAllByConfirmed();
        model.addAttribute("recipes",recipes);
        int veganQuantity = 0;
        int lowCaloQuantity = 0;
        int quickQuantity = 0;
        model.addAttribute("veganQuantity", veganQuantity);
        model.addAttribute("lowCaloQuantity", lowCaloQuantity);
        model.addAttribute("quickQuantity", quickQuantity);
        return "recipe-home";
    }
    @GetMapping("/recipe-form")
    public String recipeForm() {
        return "recipe-form";
    }

    @PostMapping("/add-recipe")
    public String addRecipe(MultipartHttpServletRequest request, Model model, Principal principal) {

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

        if (ingredientCount == 0 || stepCount == 0) {
            model.addAttribute("error", "Please add something for the recipe");
            return "index";
        }
        System.out.println("songuyenlieu:" + ingredientCount);
        System.out.println("sobuoc:" + stepCount);

        for (int i = 1; i <= ingredientCount; i++) {
            System.out.println(request.getParameter("ingredient" + i) + " " + request.getParameter("measurements" + i) + " " + request.getParameter("amount" + i));
            Ingredient ingredient = new Ingredient();
            ingredient.setName(request.getParameter("ingredient" + i));
            ingredient.setAmount(Double.parseDouble(request.getParameter("amount" + i)));
            ingredient.setMeasurement(measurementService.findByName(request.getParameter("measurements" + i)));
            ingredient.setRecipe(recipe);
            ingredients.add(ingredient);

        }
        String imgStepPath = "image-step";
        for (int i = 1; i <= stepCount; i++) {
            ImageUpload imageUpload = new ImageUpload();
            System.out.println(request.getParameter("step" + i));
            List<MultipartFile> images = request.getFiles("step" + i + "Image");

            List<ImgStep> imgSteps = new ArrayList<>();
            Step step = new Step();
            step.setDescription(request.getParameter("step" + i));
            step.setRecipe(recipe);

            for (MultipartFile image : images) {
                System.out.println(image.getOriginalFilename());
                String imageName = "images/image-step/" + imageUpload.uploadImage(image, imgStepPath);
                ImgStep imgStep = new ImgStep();
//                imgStep.setImgPath(imageUpload.getURL(imageUpload.uploadImage(image,"/recipe/"),"recipe"));;
                imgStep.setImgPath(imageName);
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
            recipe.set_confirmed(false);
            recipe.set_checked(false);
            recipe.setCustomer(customerService.findByUsername(principal.getName()));
            recipeService.save(recipe);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "index";
    }



    @GetMapping("/recipe-search")
    public String resultRecipe(@RequestParam("keyword")String keyword, Model model){
//        List<Recipe> recipes = recipeService.findAllByConfirmed();
        List<Recipe> recipes = recipeService.searchRecipes(keyword);
//        System.out.println(recipes.get(0).getName());
        model.addAttribute("recipes",recipes);
        int veganQuantity = 0;
        int lowCaloQuantity = 0;
        int quickQuantity = 0;
        model.addAttribute("veganQuantity", veganQuantity);
        model.addAttribute("lowCaloQuantity", lowCaloQuantity);
        model.addAttribute("quickQuantity", quickQuantity);
        return "recipe-home";
    }

}
