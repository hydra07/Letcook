package com.ecommerce.customer.controller;

import com.ecommerce.library.model.*;
import com.ecommerce.library.service.*;
import com.ecommerce.library.utils.ImageUpload;
import jakarta.servlet.http.HttpServletRequest;
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
import java.util.Map;

@Controller
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private FollowService followService;

    @Autowired
    private MeasurementService measurementService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/find-recipe/{id}")
    public String recipeDetail(@PathVariable("id") Long id, Model model, Principal principal){
        Recipe recipe = recipeService.getRecipeById(id);
        model.addAttribute("recipe",recipe);
        boolean isFavorite = false;
        boolean isFollowed = false;

        if(principal != null){
            isFavorite = customerService.isFavorite(id,principal.getName());
            Customer customer = customerService.findByUsername(principal.getName());
            Customer following = recipe.getCustomer();
            isFollowed = followService.isFollowing(customer.getId(),following.getId());
            model.addAttribute("isFollowed", isFollowed);
        }
        double calories = 0;
        double sugar = 0;
        double fat = 0;
        double sodium = 0;
        double carbs = 0;
        double fiber = 0;

        for(Ingredient ingredient  : recipe.getIngredients()){
            Map<String, Double> nutritions =  ingredientService.getNutrition(ingredient);
            calories += nutritions.get("calories");
            sugar += nutritions.get("sugar");
            fat += nutritions.get("fat");
            sodium += nutritions.get("sodium");
            carbs += nutritions.get("carbs");
            fiber += nutritions.get("fiber");
        }
        model.addAttribute("calories", calories);
        model.addAttribute("sugar", sugar);
        model.addAttribute("fat", fat);
        model.addAttribute("sodium", sodium);
        model.addAttribute("carbs", carbs);
        model.addAttribute("fiber", fiber);
        model.addAttribute("isFavorite", isFavorite);
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


    @GetMapping("/my-recipe")
    public String myRecipe(Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        List<Recipe> myRecipes = customer.getRecipes();
        List<Recipe> savedRecipes = customer.getFavoriteRecipes();
        model.addAttribute("myRecipes",myRecipes);
        model.addAttribute("savedRecipes",savedRecipes);

        return "my-recipe";
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

        return "redirect:/my-recipe";
    }

    @PostMapping("/add-to-favorite")
    public String addToFavorite(
            @RequestParam("id") Long id,
            Model model, Principal principal,
            HttpServletRequest request) {
        if (principal == null) {
            return "redirect:/login";
        }
        String customerName = principal.getName();
        Customer customer = customerService.addToFavourite(id, customerName);
        if (customer == null) {
            model.addAttribute("error", "Something has error");
        }
        return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping("/remove-from-favorite")
    public String removeFromFavorite(
            @RequestParam("id") Long id,
            Model model, Principal principal,
            HttpServletRequest request) {
        if (principal == null) {
            return "redirect:/login";
        }
        String customerName = principal.getName();
        Customer customer = customerService.removeFromFavorite(id, customerName);
        if (customer == null) {
            model.addAttribute("error", "Something has error");
        }
        return "redirect:" + request.getHeader("Referer");
    }


}
