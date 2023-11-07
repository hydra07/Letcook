package com.ecommerce.customer.controller;

import com.ecommerce.library.enums.NotificationType;
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
import java.util.Date;
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

    @Autowired
    private CommentService commentService;

    @Autowired
    private ProductService productService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/find-recipe/{id}")
    public String recipeDetail(@PathVariable("id") Long id, Model model, Principal principal){

        List<Comment> comments =  commentService.findAllCommentByRecipeId(id);
        model.addAttribute("comments",comments);
        Recipe recipe = recipeService.getRecipeById(id);
        if(!recipe.is_confirmed()){
            return "redirect:/recipe-home";
        }
        model.addAttribute("recipe",recipe);

        if(principal != null) {
            model.addAttribute("currentUser", customerService.findByUsername(principal.getName()));
        }else{
            model.addAttribute("currentUser", null);
        }

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

        if(recipe.is_verified()) {
            for (Ingredient ingredient : recipe.getIngredients()) {
                Map<String, Double> nutritions = ingredientService.getNutrition(ingredient);
                calories += nutritions.get("calories");
                sugar += nutritions.get("sugar");
                fat += nutritions.get("fat");
                sodium += nutritions.get("sodium");
                carbs += nutritions.get("carbs");
                fiber += nutritions.get("fiber");
            }
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
        List<Recipe> veganRecipes = recipeService.searchRecipes("chay");
        List<Recipe> lowCaloRecipes = new ArrayList<>();
        for(Recipe recipe :recipes){
            double calories = 0;
            if (recipe.is_verified()) {
                for (Ingredient ingredient : recipe.getIngredients()) {
                    calories += ingredientService.getNutrition(ingredient).get("calories");
                }
            }
            if(calories < 100 && recipe.is_verified()){
                lowCaloRecipes.add(recipe);
            }
        }
        List<Recipe> quickRecipes = new ArrayList<>();
        for(Recipe recipe :recipes){
            if(recipe.getCookingTime() < 30){
                quickRecipes.add(recipe);
            }
        }
        int veganQuantity = veganRecipes.size();
        int lowCaloQuantity = lowCaloRecipes.size();
        int quickQuantity = quickRecipes.size();
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
        boolean isVerified = true;
        List<Ingredient> ingredients = new ArrayList<>();
        List<Step> steps = new ArrayList<>();
        ImageUpload imageUpload = new ImageUpload();
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
            String ingredientName = request.getParameter("ingredient" + i);
            ingredient.setName(ingredientName);
            ingredient.setAmount(Double.parseDouble(request.getParameter("amount" + i)));
            ingredient.setMeasurement(measurementService.findByName(request.getParameter("measurements" + i)));
            ingredient.setRecipe(recipe);
            Product product = productService.getProductByName(ingredientName);
            if(product != null){
                ingredient.setProduct(product);
            }else{
                isVerified = false;
            }
            ingredients.add(ingredient);

        }
        String imgStepPath = "image-step";
        for (int i = 1; i <= stepCount; i++) {

            System.out.println(request.getParameter("step" + i));
            List<MultipartFile> images = request.getFiles("step" + i + "Image");

            List<ImgStep> imgSteps = new ArrayList<>();
            Step step = new Step();
            step.setDescription(request.getParameter("step" + i));
            step.setRecipe(recipe);

            if( images != null && images.size() > 0) {
                for (MultipartFile image : images) {
                    System.out.println(image.getOriginalFilename());
                    String imageName = "api/images/image-step/" + imageUpload.uploadImage(image, imgStepPath);
                    ImgStep imgStep = new ImgStep();
//                imgStep.setImgPath(imageUpload.getURL(imageUpload.uploadImage(image,"/recipe/"),"recipe"));;
                    imgStep.setImgPath(imageName);
                    imgStep.setStep(step);
                    imgSteps.add(imgStep);
                }
                step.setImages(imgSteps);
            }else {
                step.setImages(null); // Set the images field to null if there are no images.
            }

            steps.add(step);

        }
        MultipartFile recipeImageFile = request.getFile("recipeImage");
        String recipeImage = "api/images/recipe/" + imageUpload.uploadImage(recipeImageFile,"recipe");
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
            recipe.setCreateAt(new Date());
            recipe.setImage(recipeImage);
            recipe.set_verified(isVerified);
            recipeService.save(recipe);
        } catch (Exception e) {
            e.printStackTrace();
        }

        NotificationType acceptOrder = NotificationType.NEW_RECIPE;
        String message = "You have a new reipe to check";
        String url = "/recipes/0";
        notificationService.createNotification(acceptOrder.getTitle(), null, acceptOrder.getType(), message, url);

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

    @GetMapping("/recipe-search")
    public String resultRecipe(@RequestParam("keyword")String keyword, Model model){
//        List<Recipe> recipes = recipeService.findAllByConfirmed();
        List<Recipe> recipes = recipeService.searchRecipes(keyword);
//        System.out.println(recipes.get(0).getName());
        model.addAttribute("recipes",recipes);
        List<Recipe> veganRecipes = recipeService.searchRecipes("chay");
        List<Recipe> lowCaloRecipes = new ArrayList<>();
        for(Recipe recipe :recipes){
            double calories = 0;
            for(Ingredient ingredient  : recipe.getIngredients()){
                calories += ingredientService.getNutrition(ingredient).get("calories");
            }
            if(calories < 100){
                lowCaloRecipes.add(recipe);
            }
        }
        List<Recipe> quickRecipes = new ArrayList<>();
        for(Recipe recipe :recipes){
            if(recipe.getCookingTime() < 30){
                quickRecipes.add(recipe);
            }
        }
        int veganQuantity = veganRecipes.size();
        int lowCaloQuantity = lowCaloRecipes.size();
        int quickQuantity = quickRecipes.size();
        model.addAttribute("veganQuantity", veganQuantity);
        model.addAttribute("lowCaloQuantity", lowCaloQuantity);
        model.addAttribute("quickQuantity", quickQuantity);
        return "recipe-home";
    }
    @GetMapping("/recipe-home/vegan")
    public String veganRecipe(Model model){
        List<Recipe> recipes = recipeService.findAllByConfirmed();
        List<Recipe> veganRecipes = recipeService.searchRecipes("chay");
        List<Recipe> lowCaloRecipes = new ArrayList<>();
        for(Recipe recipe :recipes){
            double calories = 0;
            for(Ingredient ingredient  : recipe.getIngredients()){
                calories += ingredientService.getNutrition(ingredient).get("calories");
            }
            if(calories < 100){
                lowCaloRecipes.add(recipe);
            }
        }
        List<Recipe> quickRecipes = new ArrayList<>();
        for(Recipe recipe :recipes){
            if(recipe.getCookingTime() < 30){
                quickRecipes.add(recipe);
            }
        }
        model.addAttribute("recipes",veganRecipes);
        int veganQuantity = veganRecipes.size();
        int lowCaloQuantity = lowCaloRecipes.size();
        int quickQuantity = quickRecipes.size();
        model.addAttribute("veganQuantity", veganQuantity);
        model.addAttribute("lowCaloQuantity", lowCaloQuantity);
        model.addAttribute("quickQuantity", quickQuantity);
        return "recipe-home";
    }
    @GetMapping("/recipe-home/low-calo")
    public String lowCaloRecipe(Model model){
        List<Recipe> recipes = recipeService.findAllByConfirmed();
        List<Recipe> veganRecipes = recipeService.searchRecipes("chay");
        List<Recipe> lowCaloRecipes = new ArrayList<>();
        for(Recipe recipe :recipes){
            double calories = 0;
            for(Ingredient ingredient  : recipe.getIngredients()){
                calories += ingredientService.getNutrition(ingredient).get("calories");
            }
            if(calories < 100){
                lowCaloRecipes.add(recipe);
            }
        }
        List<Recipe> quickRecipes = new ArrayList<>();
        for(Recipe recipe :recipes){
            if(recipe.getCookingTime() < 30){
                quickRecipes.add(recipe);
            }
        }
        model.addAttribute("recipes",lowCaloRecipes);
        int veganQuantity = veganRecipes.size();
        int lowCaloQuantity = lowCaloRecipes.size();
        int quickQuantity = quickRecipes.size();
        model.addAttribute("veganQuantity", veganQuantity);
        model.addAttribute("lowCaloQuantity", lowCaloQuantity);
        model.addAttribute("quickQuantity", quickQuantity);
        return "recipe-home";
    }
    @GetMapping("/recipe-home/quick")
    public String quickRecipe(Model model){
        List<Recipe> recipes = recipeService.findAllByConfirmed();
        List<Recipe> veganRecipes = recipeService.searchRecipes("chay");
        List<Recipe> lowCaloRecipes = new ArrayList<>();
        for(Recipe recipe :recipes){
            double calories = 0;
            for(Ingredient ingredient  : recipe.getIngredients()){
                calories += ingredientService.getNutrition(ingredient).get("calories");
            }
            if(calories < 100){
                lowCaloRecipes.add(recipe);
            }
        }
        List<Recipe> quickRecipes = new ArrayList<>();
        for(Recipe recipe :recipes){
            if(recipe.getCookingTime() < 30){
                quickRecipes.add(recipe);
            }
        }
        model.addAttribute("recipes",quickRecipes);
        int veganQuantity = veganRecipes.size();
        int lowCaloQuantity = lowCaloRecipes.size();
        int quickQuantity = quickRecipes.size();
        model.addAttribute("veganQuantity", veganQuantity);
        model.addAttribute("lowCaloQuantity", lowCaloQuantity);
        model.addAttribute("quickQuantity", quickQuantity);
        return "recipe-home";
    }

}