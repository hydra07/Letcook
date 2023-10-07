package com.ecommerce.admin.controller;

import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Nutrition;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.NutritionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class NutritionController {
//    @Autowired
//    private NutritionService nutritionService;
//
//    @GetMapping("/nutritions")
//    public String nutritions(Model model , Principal principal) {
//        if(principal == null){
//            return "redirect:/login";
//        }
//        List<Nutrition> nutritions = nutritionService.findAll();
//        model.addAttribute("nutritions", nutritions);
//        model.addAttribute("size", nutritions.size());
//        model.addAttribute("title", "Nutrition");
//        model.addAttribute("nutritionNew", new Nutrition());
//
//        return "nutritions";
//    }
//
//    @PostMapping("/add-nutrition")
//    public String add(@ModelAttribute("nutritionNew") Nutrition nutrition, RedirectAttributes attributes) {
//        try {
//            nutritionService.save(nutrition);
//            attributes.addFlashAttribute("success", "Added successfully");
//        } catch (DataIntegrityViolationException e) {
//            e.printStackTrace();
//            attributes.addFlashAttribute("failed", "Failed to add because duplicate name");
//        } catch (Exception e) {
//            e.printStackTrace();
//            attributes.addFlashAttribute("failed", "Error server");
//        }
//        return "redirect:/nutritions";
//    }
//
//
//    @RequestMapping(value = "/findById/{nutritionId}", method = {RequestMethod.GET})
//    @ResponseBody  //tra ve json
//    public Optional<Nutrition> getById(@PathVariable(name = "nutritionId") Long nutritionId){
//        return nutritionService.findById(nutritionId);
//    }
//
//    @GetMapping("/update-nutrition")
//    public String update(Nutrition nutrition, RedirectAttributes attributes){
//        try {
//            nutritionService.update(nutrition);
//            attributes.addFlashAttribute("success", "Updated successfully");
//        }catch (DataIntegrityViolationException e){
//            e.printStackTrace();
//            attributes.addFlashAttribute("failed", "Failed to update because duplicate name");
//        }catch (Exception e){
//            e.printStackTrace();
//            attributes.addFlashAttribute("failed", "Error server");
//        }
//
//        return "redirect:/nutritions";
//    }
//
//    @RequestMapping(value = "/delete-nutrition/{nutritionId}", method = {RequestMethod.GET})
//    public String delete(@PathVariable(name = "nutritionId") Long nutritionId, RedirectAttributes attributes){
//        try {
//            nutritionService.deleteById(nutritionId);
//            attributes.addFlashAttribute("success", "Deleted successfully");
//        }catch (Exception e){
//            e.printStackTrace();
//            attributes.addFlashAttribute("failed", "Failed to deleted");
//        }
//        return "redirect:/nutritions";
//    }
//
//    @RequestMapping(value = "/enable-nutrition/{nutritionId}", method = {RequestMethod.GET})
//    public String enable(@PathVariable(name = "nutritionId") Long nutritionId, RedirectAttributes attributes){
//        try {
//            nutritionService.enableById(nutritionId);
//            attributes.addFlashAttribute("success", "Enabled successfully");
//        }catch (Exception e){
//            e.printStackTrace();
//            attributes.addFlashAttribute("failed", "Failed to enabled");
//        }
//        return "redirect:/nutritions";
//    }
}
