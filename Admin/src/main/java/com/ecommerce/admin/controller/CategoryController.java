package com.ecommerce.admin.controller;

import com.ecommerce.library.model.Category;
import com.ecommerce.library.service.CategoryService;
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
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String categories(Model model , Principal principal) {
        if(principal == null){
            return "redirect:/login";
        }
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("size", categories.size());
        model.addAttribute("title", "Category");
        model.addAttribute("categoryNew", new Category()); //khoi tao doi tuong o trang categories. sau do
                                                                       //dung th:object de lay.
        return "categories";
    }

    @PostMapping("/add-category")
    public String add(@ModelAttribute("categoryNew") Category category, RedirectAttributes attributes) {
        try {
            categoryService.save(category);
            attributes.addFlashAttribute("success", "Added successfully");
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to add because duplicate name");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Error server");
        }
        return "redirect:/categories";
    }


    @RequestMapping(value = "/findById/{categoryId}", method = {RequestMethod.GET})
    @ResponseBody  //tra ve json
    public Optional<Category> getById(@PathVariable(name = "categoryId") Long categoryId){
        return categoryService.findById(categoryId);
    }

    @GetMapping("/update-category")
    public String update(Category category, RedirectAttributes attributes){
        try {
            categoryService.update(category);
            attributes.addFlashAttribute("success", "Updated successfully");
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to update because duplicate name");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Error server");
        }

        return "redirect:/categories";
    }

    @RequestMapping(value = "/delete-category/{categoryId}", method = {RequestMethod.GET})
    public String delete(@PathVariable(name = "categoryId") Long categoryId, RedirectAttributes attributes){
        try {
            categoryService.deleteById(categoryId);
            attributes.addFlashAttribute("success", "Deleted successfully");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to deleted");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/enable-category/{categoryId}", method = {RequestMethod.GET})
    public String enable(@PathVariable(name = "categoryId") Long categoryId, RedirectAttributes attributes){
        try {
            categoryService.enableById(categoryId);
            attributes.addFlashAttribute("success", "Enabled successfully");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to enabled");
        }
        return "redirect:/categories";
    }
}
