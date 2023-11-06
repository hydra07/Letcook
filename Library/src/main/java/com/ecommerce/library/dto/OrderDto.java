package com.ecommerce.library.dto;

import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.OrderDetail;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

public class OrderDto {
    private Long id;
    private Date orderDate;
    private Date deliveryDate;
    private double totalPrice;
    private String orderStatus;
    private String shippingAddress;
    private boolean isAccepted;
    private Customer customer;

    private String paymentMethod;

    private String transactionId;

    private List<OrderDetail> orderDetailList;
}
