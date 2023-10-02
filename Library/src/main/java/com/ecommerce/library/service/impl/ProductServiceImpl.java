package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.repository.ProductRepository;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.utils.ImageUpload;
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

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageUpload imageUpload;

    @Override
    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtoList = transfer(products);

        return productDtoList;
    }


    @Override
    public Product save(ProductDto productDto, MultipartFile imageProduct) {
        String directory = "image-product";
        try {
            Product product = new Product();
            if (imageProduct == null) {
                product.setImage(null);
            } else {
                String imageName = "img/image-product/" + imageProduct.getOriginalFilename();
                product.setImage(imageName);
                imageUpload.uploadImage(imageProduct, directory);
                System.out.println("Upload image success");
            }

            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setCategory(productDto.getCategory());
            product.setCostPrice(productDto.getCostPrice());
            product.setCurrentQuantity(productDto.getCurrentQuantity());
            product.set_activated(true);
            product.set_deleted(false);
            return  productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Product update(ProductDto productDto, MultipartFile imageProduct) {
        String directory = "image-product";
        try {
            Product product = productRepository.getById(productDto.getId());
            System.out.println(product.getImage());
            // Check if there is a new image
            if (imageProduct != null) {
                if (!imageUpload.checkExisted(imageProduct, directory)) {
                    imageUpload.uploadImage(imageProduct ,directory);
                }
                String imageName = "img/image-product/" + imageProduct.getOriginalFilename();
                product.setImage(imageName);
                System.out.println("Upload image success");
            }

            // Update other product fields
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setSalePrice(productDto.getSalePrice());
            product.setCostPrice(productDto.getCostPrice());
            product.setCurrentQuantity(productDto.getCurrentQuantity());
            product.setCategory(productDto.getCategory());

            // Save the updated product
            return productRepository.save(product);
        } catch (Exception e) {
            // Handle the exception appropriately, e.g., log it or rethrow it
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
        productDto.setImage(product.getImage());
        productDto.setActivated(product.is_activated());
        productDto.setDeleted(product.is_deleted());
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


    private List<ProductDto> transfer(List<Product> products){
        List<ProductDto> productDtoList = new ArrayList<>();
        for(Product product : products){
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setCostPrice(product.getCostPrice());
            productDto.setSalePrice(product.getSalePrice());
            productDto.setCurrentQuantity(product.getCurrentQuantity());
            productDto.setCategory(product.getCategory());
            productDto.setImage(product.getImage());
            productDto.setActivated(product.is_activated());
            productDto.setDeleted(product.is_deleted());
            productDtoList.add(productDto);
        }
        return productDtoList;

    }

}
