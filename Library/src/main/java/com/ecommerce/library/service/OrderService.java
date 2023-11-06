package com.ecommerce.library.service;

import com.ecommerce.library.model.Order;
import com.ecommerce.library.model.ShoppingCart;

import java.util.List;

public interface OrderService {
    void saveOrder(ShoppingCart cart , String shippingAddress, String paymentMethod, String transactionNo);

    void acceptOrder(Long id);

    void cancelOrder(Long id);

    void rejectOrder(Long id);

    void orderSuccess(Long id);

    void orderUnsuccessful(Long id);

    Order getOrderById(Long id);

    void deleteOrder(Long id);

    List<Order> findAll();

    int numOfOrderToday();

    Double[] revenueByMonths();

    List<Order> getUnCheckedOrder();

    int numOfOrderByStatus(String status);
}
