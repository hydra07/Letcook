package com.ecommerce.library.service;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Product;
import net.minidev.json.JSONArray;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    //ADMIN
    List<ProductDto> findAll();

    //    Product save(MultipartFile imageProduct, ProductDto productDto);
    Product update(ProductDto productDto, List<MultipartFile> imageProducts);

    Product save(ProductDto productDto, List<MultipartFile> imageProducts);

    void deleteById(Long id);

    void enableById(Long id);


    ProductDto getById(Long id);

    Page<ProductDto> pageProducts(int pageNo);

    Page<ProductDto> searchProducts(int pageNo, String keyword);




    //CUSTOMER
    List<Product> getAllProducts();

    List<Product> listViewProducts();

    Product getProductById(Long id);

    List<Product> getRelatedProducts(Long categoryId);


    public List<Product> getProductsInCategory(Long categoryId);

    public Product getProductByName(String name);

    List<Product> searchProducts(String keyword);

    List<Product> searchProductsList(String keyword);

    JSONArray getAllProductsJson();

    JSONArray getSuggestProducts(String query);

    List<ProductDto> findAllByActivated();

    List<Product> getProductBySelling();

}
