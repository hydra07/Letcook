package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.*;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.service.RecipeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RecipeService recipeService;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String home(Model model, Principal principal, HttpSession session) {
        model.addAttribute("title", "Home");
        model.addAttribute("page", "Home");
        if (principal != null) {
            Customer customer = customerService.findByUsername(principal.getName());
            session.setAttribute("username", customer.getFirstName() + " " + customer.getLastName());
            session.setAttribute("customerUsername", customer.getUsername());
            ShoppingCart shoppingCart = customer.getShoppingCart();
            List<Notification> notifications = customer.getNotifications();
            notifications.sort((n1, n2) -> n2.getId().compareTo(n1.getId()));
            if (notifications == null || notifications.size() == 0) {
                session.setAttribute("checkNotify", "No notification");
                session.removeAttribute("notifications");
            } else {
                session.setAttribute("notifications", notifications);
                session.removeAttribute("checkNotify");
            }
            if (shoppingCart != null) {
                session.setAttribute("totalItems", shoppingCart.getTotalItems());
            }
        }
        return "home";
    }
    @GetMapping("/home")
    public String index(Model model) {
        List<Category> categories = categoryService.findAllByActivated();
        List<ProductDto> productDtos = productService.findAllByActivated();
        model.addAttribute("categories", categories);
        model.addAttribute("products", productDtos);
        return "index";
    }

    @GetMapping("/search-recipe")
    public String searchRecipe(@RequestParam("keyword") String keyword, Model model) {
        List<Recipe> recipes = recipeService.searchRecipes(keyword);
        model.addAttribute("recipes", recipes);
        return "";
    }
}
