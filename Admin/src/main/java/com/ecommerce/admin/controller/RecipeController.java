package com.ecommerce.admin.controller;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.dto.RecipeDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Ingredient;
import com.ecommerce.library.model.Measurement;
import com.ecommerce.library.model.Recipe;
import com.ecommerce.library.service.MeasurementService;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.service.RecipeService;
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
    @GetMapping("/recipes")
    public String recipes(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<Recipe> recipes = recipeService.findAll();
        model.addAttribute("title", "Recipes");
        model.addAttribute("recipes" , recipes);
        model.addAttribute("size", recipes.size());
        return "recipes";
    }

    @GetMapping("/recipes/{pageNo}")
    public String productsPage(@PathVariable("pageNo") int pageNo, Model model, Principal principal){
        if(principal == null){
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
    public String checkRecipeForm(@PathVariable("id") Long id, Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        model.addAttribute("title", "Check recipe");
        RecipeDto recipeDto = recipeService.getById(id);
        List<Measurement> measurements = measurementService.findAllByActivated();
        model.addAttribute("measurements", measurements);
        model.addAttribute("recipeDto", recipeDto);
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

    @RequestMapping(value = "/check-recipe/{id}", method = RequestMethod.POST, params = "action=accept")
    public String acceptRecipe(@PathVariable("id") Long id,
                                @ModelAttribute("recipeDto") RecipeDto recipeDto,
                                @RequestParam("productId") List<Long> productId,
                                RedirectAttributes attributes)
    {
        try {
            for(int i = 0; i < recipeDto.getIngredients().size(); i++){
                recipeDto.getIngredients().get(i).setProduct(productService.getProductById(productId.get(i)));
            }

            recipeService.update(recipeDto);
            attributes.addFlashAttribute("success", "Update successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to update!");
        }
        return "redirect:/recipes/0";
    }

    @RequestMapping(value = "/check-recipe/{id}", method = RequestMethod.POST, params = "action=reject")
    public String rejectRecipe(@PathVariable("id") Long id,
                               @ModelAttribute("recipeDto") RecipeDto recipeDto,
                               @RequestParam("recipeId") List<Long> recipeIds,
                               RedirectAttributes attributes)
    {
        try {
            recipeService.reject(recipeDto);
            attributes.addFlashAttribute("success", "Reject successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to Reject!");
        }
        return "redirect:/recipes/0";
    }
}
