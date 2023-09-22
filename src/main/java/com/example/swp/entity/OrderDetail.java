package com.example.swp.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "OrderDetail")
@Data
public class OrderDetail {
    @EmbeddedId
    //2 khóa chính là orderId và productId do đó tạo OderDetailId để lưu 2 khóa chính này
    private OrderDetailId id;
    private double unitPrice;
    private short quantity;

    // Getters and setters

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "orderId")
    private Orders order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "productId")
    private Product product;

    // Constructors
}

