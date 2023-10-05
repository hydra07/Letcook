package com.ecommerce.library.repository;

import com.ecommerce.library.model.ImgProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImgProductRepository extends JpaRepository<ImgProduct, Long> {

    @Modifying
    @Query("DELETE FROM ImgProduct ip WHERE ip.product.id = :productId")
    void deleteByProductId(@Param("productId") Long productId);
}
