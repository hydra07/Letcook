package com.ecommerce.admin.controller;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.dto.RecipeDto;
import com.ecommerce.library.enums.NotificationType;
import com.ecommerce.library.model.*;
import com.ecommerce.library.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @Autowired
    MeasurementService measurementService;

    @Autowired
    ProductService productService;

    @Autowired
    FollowService followService;

    @Autowired
    NotificationService notificationService;

//    @GetMapping("/recipes")
//    public String recipes(Model model, Principal principal) {
//        if (principal == null) {
//            return "redirect:/login";
//        }
//        List<Recipe> recipes = recipeService.findAll();
//        model.addAttribute("title", "Recipes");
//        model.addAttribute("recipes" , recipes);
//        model.addAttribute("size", recipes.size());
//        return "recipes";
//    }

    @GetMapping("/recipes/{pageNo}")
    public String productsPage(@PathVariable("pageNo") int pageNo, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Page<RecipeDto> recipes = recipeService.pageRecipes(pageNo);
        model.addAttribute("title", "Manage Recipe");
        model.addAttribute("size", recipes.getSize());
        model.addAttribute("totalPages", recipes.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("recipes", recipes);
        return "recipes";
    }


    @GetMapping("/check-recipe/{id}")
    public String checkRecipeForm(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Check recipe");
        Recipe recipe = recipeService.getRecipeById(id);
        RecipeDto recipeDto = recipeService.getById(id);
        List<Measurement> measurements = measurementService.findAllByActivated();
        model.addAttribute("measurements", measurements);
        model.addAttribute("recipeDto", recipe);
        return "check-recipe";
    }


//    @PostMapping("/check-recipe/{id}")
//    public String processUpdate(@PathVariable("id") Long id,
//                                @ModelAttribute("recipeDto") RecipeDto recipeDto,
//                                @RequestParam("productId") List<Long> productIds,
//                                RedirectAttributes attributes)
//    {
//        try {
//            recipeService.update(recipeDto);
//            attributes.addFlashAttribute("success", "Update successfully!");
//        }catch (Exception e){
//            e.printStackTrace();
//            attributes.addFlashAttribute("error", "Failed to update!");
//        }
//        return "recipes";
//    }

    //    @RequestMapping(value = "/check-recipe/{id}", method = RequestMethod.POST, params = "action=accept")
//    public String acceptRecipe(@PathVariable("id") Long id,
//                               @ModelAttribute("recipeDto") RecipeDto recipeDto,
//                               @RequestParam("productId") List<Long> productId,
//                               RedirectAttributes attributes) {
//        try {
//            for (int i = 0; i < recipeDto.getIngredients().size(); i++) {
//                recipeDto.getIngredients().get(i).setProduct(productService.getProductById(productId.get(i)));
//            }
//
//            Recipe recipe = recipeService.update(recipeDto);
//            Customer customer = recipe.getCustomer();
//            NotificationType accept = NotificationType.ACCEPT;
//            String acceptMessage = "Your recipe " + recipe.getName() + " has been accepted";
//            String acceptUrl = "/find-recipe/" + recipe.getId();
//            notificationService.createNotification(accept.getTitle(), customer, accept.getType(), acceptMessage, acceptUrl);
//
//
//            List<Customer> follows = followService.findAllByFollowingId(recipe.getCustomer().getId());
//            for (Customer follow : follows) {
//                NotificationType newRecipe = NotificationType.NEW;
//                String newRecipeMessage = "New recipe from " + recipe.getCustomer().getName() + " has been created";
//                String newRecipeUrl = "/find-recipe/" + recipe.getId();
//                notificationService.createNotification(newRecipe.getTitle(), follow, newRecipe.getType(), newRecipeMessage, newRecipeUrl);
//            }
//
//            attributes.addFlashAttribute("success", "Update successfully!");
//        } catch (Exception e) {
//            e.printStackTrace();
//            attributes.addFlashAttribute("error", "Failed to update!");
//        }
//        return "redirect:/recipes/0";
//    }
//
//    @RequestMapping(value = "/check-recipe/{id}", method = RequestMethod.POST, params = "action=reject")
//    public String rejectRecipe(@PathVariable("id") Long id,
//                               @ModelAttribute("recipeDto") RecipeDto recipeDto,
//
//                               @RequestParam("rejectReason") String rejectReason,
//                               RedirectAttributes attributes) {
//        try {
//            recipeService.reject(recipeDto);
//            attributes.addFlashAttribute("success", "Reject successfully!");
//            Recipe recipe = recipeService.reject(recipeDto);
//            Customer customer = recipe.getCustomer();
//            NotificationType accept = NotificationType.REJECT;
//            String message = "Your recipe" + recipe.getName() + " has been rejected with reason: "+rejectReason;
//            String url = "/my-recipe";
//            notificationService.createNotification(accept.getTitle(), customer, accept.getType(), message, url);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            attributes.addFlashAttribute("error", "Failed to Reject!");
//        }
//        return "redirect:/recipes/0";
//    }
    @RequestMapping(value = "/check-recipe/{id}", method = RequestMethod.POST, params = "action=accept")
    public String acceptRecipe(@PathVariable("id") Long id,
                               RedirectAttributes attributes) {
        try {

            Recipe recipe = recipeService.getRecipeById(id);
            recipeService.update(recipe);
            Customer customer = recipe.getCustomer();
            NotificationType accept = NotificationType.ACCEPT;
            String acceptMessage = "Your recipe " + recipe.getName() + " has been accepted";
            String acceptUrl = "/find-recipe/" + recipe.getId();
            notificationService.createNotification(accept.getTitle(), customer, accept.getType(), acceptMessage, acceptUrl);


            List<Customer> follows = followService.findAllByFollowingId(recipe.getCustomer().getId());
            for (Customer follow : follows) {
                NotificationType newRecipe = NotificationType.NEW;
                String newRecipeMessage = "New recipe from " + recipe.getCustomer().getName() + " has been created";
                String newRecipeUrl = "/find-recipe/" + recipe.getId();
                notificationService.createNotification(newRecipe.getTitle(), follow, newRecipe.getType(), newRecipeMessage, newRecipeUrl);
            }

            attributes.addFlashAttribute("success", "Update successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to update!");
        }
        return "redirect:/recipes/0";
    }

    @RequestMapping(value = "/check-recipe/{id}", method = RequestMethod.POST, params = "action=reject")
    public String rejectRecipe(@PathVariable("id") Long id,
                               @RequestParam("rejectReason") String rejectReason,
                               RedirectAttributes attributes) {
        try {
            Recipe recipe = recipeService.getRecipeById(id);
            recipeService.reject(recipe);
            attributes.addFlashAttribute("success", "Reject successfully!");
            Customer customer = recipe.getCustomer();
            NotificationType accept = NotificationType.REJECT;
            String message = "Your recipe" + recipe.getName() + " has been rejected with reason: " + rejectReason;
            String url = "/my-recipe";
            notificationService.createNotification(accept.getTitle(), customer, accept.getType(), message, url);

        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to Reject!");
        }
        return "redirect:/recipes/0";
    }
}
