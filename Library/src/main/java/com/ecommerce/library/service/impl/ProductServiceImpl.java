package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.ImgProduct;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.repository.ImgProductRepository;
import com.ecommerce.library.repository.ProductRepository;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.utils.ImageUpload;
import jakarta.transaction.Transactional;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageUpload imageUpload;

    @Autowired
    private ImgProductRepository imgProductRepository;


    //ADMIN
    @Override
    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtoList = transfer(products);

        return productDtoList;
    }



    @Override
    public Product save(ProductDto productDto, List<MultipartFile> imageProducts) {
        String directory = "image-product";
        try {
            Product product = new Product();

            List<String> imageNames = new ArrayList<>();
            List<ImgProduct> imgProducts = new ArrayList<>();
            for (MultipartFile imageProduct : imageProducts) {
                if (imageProduct != null && !imageProduct.isEmpty()) {
                    String imageName = "api/images/image-product/" + imageUpload.uploadImage(imageProduct, directory);
                    imageNames.add(imageName);

                    // Create an ImgProduct and set the imgPath
                    ImgProduct imgProduct = new ImgProduct();
                    imgProduct.setImgPath(imageName);
                    imgProduct.setProduct(product);

                    // Add the ImgProduct to the list of imgProducts
                    imgProducts.add(imgProduct);

                    System.out.println("Uploaded image: " + imageName);
                }
            }

            // Set the list of ImgProduct objects in the Product entity
            product.setImgProducts(imgProducts);

            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setCategory(productDto.getCategory());
            product.setCostPrice(productDto.getCostPrice());
            product.setCurrentQuantity(productDto.getCurrentQuantity());
            product.set_activated(true);
            product.set_deleted(false);
            product.setAmountToSell(productDto.getAmountToSell());
            product.setMeasurement(productDto.getMeasurement());
            product.setCalories(productDto.getCalories());
            product.setSugar(productDto.getSugar());
            product.setFat(productDto.getFat());
            product.setSodium(productDto.getSodium());
            product.setCarbs(productDto.getCarbs());
            product.setFiber(productDto.getFiber());
            product.setAverageWeight(productDto.getAverageWeight());

            return productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    @Transactional
    public Product update(ProductDto productDto, List<MultipartFile> imageProducts) {
        try {
            Product product = productRepository.getById(productDto.getId());
            System.out.println("size:" + imageProducts.size());
            System.out.println("checkk:" + imageProducts.get(0).isEmpty());
            System.out.println(imageProducts.get(0));
            // Get the current list of ImgProducts
            List<ImgProduct> currentImages = product.getImgProducts();
            System.out.println("currentsize: "+currentImages.get(0).getImgPath());

            if (imageProducts != null && !imageProducts.get(0).isEmpty()) {
                // Delete the old images from the database and storage
                imgProductRepository.deleteByProductId(productDto.getId());
                for (ImgProduct imgProduct : currentImages) {
                    imageUpload.deleteImage(imgProduct.getImgPath());
                }

                // Process and save new images
                List<ImgProduct> newImages = new ArrayList<>();
                String directory = "image-product";

                for (MultipartFile imageProduct : imageProducts) {
                    if (!imageProduct.isEmpty()) {
                        String imageName = "api/images/image-product/" + imageUpload.uploadImage(imageProduct, directory);

                        // Create a new ImgProduct and set the imgPath
                        ImgProduct newImgProduct = new ImgProduct();
                        newImgProduct.setImgPath(imageName);
                        newImgProduct.setProduct(product);

                        // Add the new ImgProduct to the list of newImages
                        newImages.add(newImgProduct);
                        System.out.println("Uploaded image: " + imageName);
                    }
                }

                // Set the new list of ImgProducts for the product
                product.setImgProducts(newImages);
            }

            // Update other product properties
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setCategory(productDto.getCategory());
            product.setCostPrice(productDto.getCostPrice());
            product.setCurrentQuantity(productDto.getCurrentQuantity());
            product.set_activated(true);
            product.set_deleted(false);
            product.setAmountToSell(productDto.getAmountToSell());
            product.setMeasurement(productDto.getMeasurement());
            product.setCalories(productDto.getCalories());
            product.setSugar(productDto.getSugar());
            product.setFat(productDto.getFat());
            product.setSodium(productDto.getSodium());
            product.setCarbs(productDto.getCarbs());
            product.setFiber(productDto.getFiber());
            product.setAverageWeight(productDto.getAverageWeight());
            // Save the updated product
            return productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update product");
        }
    }


    @Override
    public void deleteById(Long id) {
        Product product = productRepository.getById(id);
        product.set_deleted(true);
        product.set_activated(false);
        productRepository.save(product);
    }

    @Override
    public void enableById(Long id) {
        Product product = productRepository.getById(id);
        product.set_deleted(false);
        product.set_activated(true);
        productRepository.save(product);
    }


    @Override
    public ProductDto getById(Long id) {
        Product product = productRepository.getById(id);
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setCostPrice(product.getCostPrice());
        productDto.setSalePrice(product.getSalePrice());
        productDto.setCurrentQuantity(product.getCurrentQuantity());
        productDto.setCategory(product.getCategory());
        productDto.setActivated(product.is_activated());
        productDto.setDeleted(product.is_deleted());
        productDto.setImgProducts(product.getImgProducts().stream()
                .map(ImgProduct::getImgPath)
                .collect(Collectors.toList()));
        productDto.setAmountToSell(product.getAmountToSell());
        productDto.setMeasurement(product.getMeasurement());
        productDto.setCalories(product.getCalories());
        productDto.setSugar(product.getSugar());
        productDto.setFat(product.getFat());
        productDto.setSodium(product.getSodium());
        productDto.setCarbs(product.getCarbs());
        productDto.setFiber(product.getFiber());
        productDto.setAverageWeight(product.getAverageWeight());
        return productDto;
    }

    @Override
    public Page<ProductDto> pageProducts(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<ProductDto> products = transfer(productRepository.findAll());
        Page<ProductDto> productPages = toPage(products, pageable);

        return productPages;
    }

    private Page toPage(List<ProductDto> list, Pageable pageable){
        if(pageable.getOffset() >= list.size()){
            return Page.empty();
        }
        int startIndex = (int)pageable.getOffset();
        int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ?
                list.size() : pageable.getOffset() + pageable.getPageSize());

        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

    @Override
    public Page<ProductDto> searchProducts(int pageNo, String keyword) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<ProductDto> productDtoList = transfer(productRepository.searchProductsList(keyword));
        Page<ProductDto> products = toPage(productDtoList, pageable);
        return products;
    }



    @Override
    public List<Product> getProductsInCategory(Long categoryId) {
        return productRepository.getProductsInCategory(categoryId);
    }

    @Override
    public Product getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        return null;
    }


    private List<ProductDto> transfer(List<Product> products) {
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setCostPrice(product.getCostPrice());
            productDto.setSalePrice(product.getSalePrice());
            productDto.setCurrentQuantity(product.getCurrentQuantity());
            productDto.setCategory(product.getCategory());
            productDto.setImgProducts(product.getImgProducts().stream()
                    .map(ImgProduct::getImgPath)
                    .collect(Collectors.toList()));
            productDto.setActivated(product.is_activated());
            productDto.setDeleted(product.is_deleted());
            productDto.setImgProducts(product.getImgProducts().stream()
                    .map(ImgProduct::getImgPath)
                    .collect(Collectors.toList()));
            productDto.setAmountToSell(product.getAmountToSell());
            productDto.setMeasurement(product.getMeasurement());
            productDto.setCalories(product.getCalories());
            productDto.setSugar(product.getSugar());
            productDto.setFat(product.getFat());
            productDto.setSodium(product.getSodium());
            productDto.setCarbs(product.getCarbs());
            productDto.setFiber(product.getFiber());
            productDto.setAverageWeight(product.getAverageWeight());

            productDtoList.add(productDto);
        }
        return productDtoList;

    }


    //CUSTOMER
    //get all products to display in shop page
    @Override
    public List<Product> getAllProducts() {
        return productRepository.getAllProduct();
    }

    @Override
    public List<Product> listViewProducts() {
        return productRepository.listViewProducts();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.getById(id);
    }

    @Override
    public List<Product> getRelatedProducts(Long categoryId) {
        return productRepository.getRelatedProduct(categoryId);
    }

    @Override
    public List<Product> searchProductsList(String keyword) {
        return productRepository.searchProductsList(keyword);
    }

    @Override
    public JSONArray getAllProductsJson() {
        List<Product> products = productRepository.getAllProduct();
        JSONArray jsonArray = new JSONArray();
        for (Product product : products) {
            jsonArray.add(product.getName());
        }
        return jsonArray;
    }

    @Override
    public JSONArray getSuggestProducts(String query) {
        List<Product> products = productRepository.searchProductsList(query);
        JSONArray jsonArray = new JSONArray();
        for (Product product : products) {
            jsonArray.add(product.getName());
        }
        return jsonArray;
    }

    @Override
    public List<ProductDto> findAllByActivated() {
        List<Product> products =  productRepository.findAllByActivated();
        List<ProductDto> productDtoList = transfer(products);
        return productDtoList;
    }

    @Override
    public List<Product> getProductBySelling() {
        return productRepository.sortByQuantitySell();
    }


}
