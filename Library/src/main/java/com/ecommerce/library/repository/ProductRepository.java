package com.ecommerce.library.repository;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p")
    Page<Product> pageProduct(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
    Page<Product> searchProducts(String keywords, Pageable pageable);


    @Query("select p from Product p where p.is_deleted = false and p.is_activated = true")
    List<Product> getAllProduct();

//    @Query
//    List<Product> getSellProduct();

    @Query(value = "SELECT TOP 4 * FROM products WHERE is_deleted = 0 AND is_activated = 1 ORDER BY NEWID();", nativeQuery = true)
    List<Product> listViewProducts();

    /*
    * @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
       List<Product> getRelatedProduct(@Param("categoryId") Long categoryId);
    * */
    @Query(value = "SELECT * FROM products p WHERE p.category_id = ?1 and is_activated = 1 and is_deleted = 0", nativeQuery = true)
    List<Product> getRelatedProduct(Long categoryId);

    @Query(value = "select p from Product p inner join Category c on c.id = ?1 and p.category.id = ?1 where p.is_activated = true and p.is_deleted = false")
    List<Product> getProductsInCategory(Long id);


    @Query(value = "SELECT p FROM Product p inner join Category c on p.category.id =  c.id WHERE p.is_deleted = false AND p.is_activated = true  and  c.is_activated = true and c.is_deleted = false")
    List<Product> findAllByActivated();


    @Query("select p from Product p where p.name like %?1% or p.description like %?1%")
    List<Product> findAllByNameOrDescription(String keyword);

    //
//
//    @Query("select p from Product p inner join Category c ON c.id = p.category.id" +
//            " where p.category.name = ?1 and p.is_activated = true and p.is_deleted = false")
//    List<Product> findAllByCategory(String category);
//
//    @Query(value = "select " +
//            "p.product_id, p.name, p.description, p.current_quantity, p.cost_price, p.category_id, p.sale_price, p.image, p.is_activated, p.is_deleted " +
//            "from products p where p.is_activated = true and p.is_deleted = false order by rand() limit 9", nativeQuery = true)
//    List<Product> randomProduct();
//
//    @Query(value = "select " +
//            "p.product_id, p.name, p.description, p.current_quantity, p.cost_price, p.category_id, p.sale_price, p.image, p.is_activated, p.is_deleted " +
//            "from products p where p.is_deleted = false and p.is_activated = true order by p.cost_price desc limit 9", nativeQuery = true)
//    List<Product> filterHighProducts();
//
//    @Query(value = "select " +
//            "p.product_id, p.name, p.description, p.current_quantity, p.cost_price, p.category_id, p.sale_price, p.image, p.is_activated, p.is_deleted " +
//            "from products p where p.is_deleted = false and p.is_activated = true order by p.cost_price asc limit 9", nativeQuery = true)
//    List<Product> filterLowerProducts();
//
//
//    @Query(value = "select p.product_id, p.name, p.description, p.current_quantity, p.cost_price, p.category_id, p.sale_price, p.image, p.is_activated, p.is_deleted from products p where p.is_deleted = false and p.is_activated = true limit 4", nativeQuery = true)
//    List<Product> listViewProduct();
//
//
//    @Query(value = "select p from Product p inner join Category c on c.id = ?1 and p.category.id = ?1 where p.is_activated = true and p.is_deleted = false")
//    List<Product> getProductByCategoryId(Long id);
//
//
    @Query("select p from Product p where p.name like %?1% or p.description like %?1%")
    List<Product> searchProductsList(String keyword);


    @Query(value = "SELECT p.* FROM products p LEFT JOIN ( SELECT product_id, SUM(quantity) AS total_quantity_sold FROM order_detail GROUP BY product_id ) AS quantities ON p.product_id = quantities.product_id ORDER BY COALESCE(quantities.total_quantity_sold, 0) DESC;", nativeQuery = true)
    List<Product> sortByQuantitySell();

    @Query(value = "SELECT TOP 1 * FROM ( " +
            "  SELECT *, LEN(name) as name_length, 0 as tag_length FROM products WHERE name LIKE %:name% " +
            "  UNION ALL " +
            "  SELECT *, 0 as name_length, LEN(tags) as tag_length FROM products WHERE tags LIKE %:name% " +
            ") result " +
            "ORDER BY name_length, tag_length", nativeQuery = true)
    Product findByName(@Param("name") String name);


}
