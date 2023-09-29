package com.ecommerce.admin.controller;


import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;
import jakarta.mail.Multipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class ProductController {


    @Autowired
    CategoryService categoryService;

    @Autowired
    private ProductService productService;


    @GetMapping("/products")
    public String products(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<ProductDto> productDtoList = productService.findAll();
        model.addAttribute("products", productDtoList);
        model.addAttribute("title", "Manage Products");
        model.addAttribute("size", productDtoList.size());
        return "products";

    }

    @GetMapping("/add-product")
    public String addProduct(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAllByActivated());
        return "add-product";
    }

    @PostMapping("/save-product")
    public String saveProduct(@ModelAttribute("product") ProductDto product
            , @RequestParam("imageProduct") MultipartFile imageProduct
            , RedirectAttributes attributes
    ) {
        try {
            productService.save(product, imageProduct);
            attributes.addFlashAttribute("message", "The product has been saved successfully");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Failed to add product");
            e.printStackTrace();
        }
        return "redirect:/products";
    }

    @GetMapping("update-product/{id}")
    public String updateProductForm(@PathVariable("id") Long id, Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        model.addAttribute("title","Update product");
        List<Category> categories = categoryService.findAllByActivated();
        ProductDto productDto = productService.getById(id);
        model.addAttribute(("categories"), categories);
        model.addAttribute("productDto", productDto);
        return "update-product";
    }

    @PostMapping("/update-product/{id}")
    public String processUpdate(@PathVariable("id") Long id,
                                @ModelAttribute("productDto") ProductDto productDto,
                                RedirectAttributes attributes,
                                @RequestParam("imageProduct") MultipartFile imageProduct
                                ){
        try {
            productService.update(productDto, imageProduct);
            attributes.addFlashAttribute("message", "The product has been updated successfully");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to update product");
        }
        return  "redirect:/products";
    }


    @RequestMapping(value ="/enable-product/{id}", method = {RequestMethod.GET, RequestMethod.PUT})
    public String enableProduct(@PathVariable("id") Long id, RedirectAttributes attributes){
        try {
            productService.enableById(id);
            attributes.addFlashAttribute("message", "The product has been enabled successfully");
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/products";
    }

    @RequestMapping(value ="/delete-product/{id}", method = {RequestMethod.GET, RequestMethod.PUT})
    public String deletedProduct(@PathVariable("id") Long id, RedirectAttributes attributes){
        try {
            productService.deleteById(id);
            attributes.addFlashAttribute("message", "The product has been deleted  ");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to delete product");
        }
        return "redirect:/products";
    }

}
