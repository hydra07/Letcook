package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("products")
    public String products(Model model){
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        List<Product> products = productService.getAllProducts();
        List<Product> listViewProducts = productService.listViewProducts();
        System.out.println("sizee:"+listViewProducts.get(0).getName());
        model.addAttribute("categories", categoryDtoList);
        model.addAttribute("products", products);
        model.addAttribute(("viewProducts"), listViewProducts);

        return "shop";
    }

    @GetMapping("find-product/{id}")
    public String findProductById(@PathVariable("id") Long id,Model model){
        Product product = productService.getProductById(id);
        List<Product> relatedProducts = productService.getRelatedProducts(id);
        model.addAttribute("product", product);
        model.addAttribute("relatedProducts", relatedProducts);

        return "product-detail";
    }


}
