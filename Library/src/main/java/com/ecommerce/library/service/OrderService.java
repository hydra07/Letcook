package com.ecommerce.library.service;

import com.ecommerce.library.model.Order;
import com.ecommerce.library.model.ShoppingCart;

import java.util.List;

public interface OrderService {
    void saveOrder(ShoppingCart cart , String shippingAddress, String paymentMethod);

    void acceptOrder(Long id);

    void cancelOrder(Long id);

    Order getOrderById(Long id);

    void deleteOrder(Long id);

    List<Order> findAll();


}
