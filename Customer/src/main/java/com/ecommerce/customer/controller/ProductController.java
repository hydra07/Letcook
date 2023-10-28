package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("products")
    public String products(Model model) {
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        List<Product> products = productService.getAllProducts();
        List<Product> listViewProducts = productService.listViewProducts();
        System.out.println("sizee:"+listViewProducts.get(0).getName());
        model.addAttribute("categories", categoryDtoList);
        model.addAttribute("products", products);
        model.addAttribute(("viewProducts"), listViewProducts);
        model.addAttribute("keyword",null);

        return "shop";
    }

    @GetMapping("find-product/{id}")
    public String findProductById(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        List<Product> relatedProducts = productService.getRelatedProducts(product.getCategory().getId());
        System.out.println("size:" + relatedProducts.size());
        model.addAttribute("product", product);
        model.addAttribute("relatedProducts", relatedProducts);

        return "product-detail";
    }

    @GetMapping("/products-in-category/{id}")
    public String getProductsInCategory(@PathVariable("id") Long categoryId, Model model) {
        Optional<Category> category = categoryService.findById(categoryId);
        List<CategoryDto> categories = categoryService.getCategoryAndProduct();
        List<Product> products = productService.getProductsInCategory(categoryId);
        List<Product> listViewProducts = productService.listViewProducts();

        model.addAttribute(("viewProducts"), listViewProducts);
        model.addAttribute("category", category);
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        return "shop";
    }
    @GetMapping("/search-result")
    public String searchResult(@RequestParam("keyword")String keyword, Model model){
//        List<Product> products = productService.getAllProducts();
        List<Product> products = productService.searchProductsList(keyword);
        model.addAttribute("products", products);
        return "shop";
    }

    @GetMapping("/search-product")
    public String searchProduct(@RequestParam("keyword") String keyword, Model model) {
        List<CategoryDto> categoryDtos = categoryService.getCategoryAndProduct();
        List<Product> products = productService.searchProducts(keyword);
        List<Product> listViewProducts = productService.listViewProducts();
        model.addAttribute("listViewProducts", listViewProducts);
        model.addAttribute("categories", categoryDtos);
        model.addAttribute("title", "Search Products");
        model.addAttribute("page", "Result Search");
        model.addAttribute("products", products);
        return "shop";
    }

    @GetMapping("/process-selection")
    public String processSelection(@RequestParam(name = "selectedValue") String selectedValue) {
        // Xử lý giá trị đã chọn ở đây
        System.out.println("Selected value: " + selectedValue);
        // Redirect hoặc trả về trang web khác
        return "redirect:/another-page";
    }

    @GetMapping("/products/1")
    public String products_high_price_to_low(Model model){
//        List<ProductDto> productDtoList = productService.findAllByActivated();
//        productDtoList.sort((o1, o2) -> Double.compare(o2.getCostPrice(), o1.getCostPrice()));
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        List<Product> products = productService.getAllProducts();
        products.sort((o1, o2) -> Double.compare(o2.getCostPrice(), o1.getCostPrice()));
        List<Product> listViewProducts = productService.listViewProducts();
        System.out.println("sizee:"+listViewProducts.get(0).getName());
        model.addAttribute("categories", categoryDtoList);
        model.addAttribute("products", products);
        model.addAttribute(("viewProducts"), listViewProducts);
        model.addAttribute("keyword",null);

        return "shop";
    }
    @GetMapping("/products/2")
    public String products_low_price_to_high(Model model, Principal principal){
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        List<Product> products = productService.getAllProducts();
        products.sort((o1, o2) -> Double.compare(o1.getCostPrice(), o2.getCostPrice()));
        List<Product> listViewProducts = productService.listViewProducts();
        System.out.println("sizee:"+listViewProducts.get(0).getName());
        model.addAttribute("categories", categoryDtoList);
        model.addAttribute("products", products);
        model.addAttribute(("viewProducts"), listViewProducts);
        model.addAttribute("keyword",null);

        return "shop";
    }
    @GetMapping("/products/3")
    public String products_bestselling(Model model, Principal principal){

        List<Product> products = productService.getProductBySelling();
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();


        List<Product> listViewProducts = productService.listViewProducts();
        System.out.println("sizee:"+listViewProducts.get(0).getName());
        model.addAttribute("categories", categoryDtoList);
        model.addAttribute("products", products);
        model.addAttribute(("viewProducts"), listViewProducts);
        model.addAttribute("keyword",null);
        return "shop";
    }




}
