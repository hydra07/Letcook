package com.ecommerce.customer.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Enumeration;
import java.util.List;

@Controller
public class RecipeController {
    @GetMapping("/recipe-form")
    public String recipeForm() {
        return "recipe-form";
    }

    @PostMapping("/recipe-form")
    public String recipeSubmit(HttpServletRequest request) {
        return "recipe-form";
    }


}
