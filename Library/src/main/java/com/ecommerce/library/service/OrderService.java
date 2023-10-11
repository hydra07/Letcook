package com.ecommerce.library.service;

import com.ecommerce.library.model.ShoppingCart;

public interface OrderService {
    void saveOrder(ShoppingCart cart , String shippingAddress);

    void acceptOrder(Long id);

    void cancelOrder(Long id);
}
