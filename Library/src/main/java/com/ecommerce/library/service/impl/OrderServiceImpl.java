package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.*;
import com.ecommerce.library.repository.*;
import com.ecommerce.library.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    static final String PENDING = "PENDING";
    static final String SHIPPING = "SHIPPING";
    static final String CANCELED = "CANCELED";

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShoppingCartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.getById(id);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }


    @Override
    public void saveOrder(ShoppingCart cart, String shippingAddress, String paymentMethod) {
        Order order = new Order();
        order.setOrderStatus(PENDING);
        order.setOrderDate((new Date()));
        order.setCustomer(cart.getCustomer());
        order.setTotalPrice(cart.getTotalPrices());
        order.setShippingAddress(shippingAddress);
        order.setPaymentMethod(paymentMethod);
        order.setAccepted(false);
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (CartItem item : cart.getCartItem()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setUnitPrice(item.getProduct().getCostPrice());
            orderDetail.setProduct(item.getProduct());
            orderDetailRepository.save(orderDetail);
            orderDetailList.add(orderDetail);
            System.out.println();
            cartItemRepository.delete(item);
        }

        order.setOrderDetailList(orderDetailList);
        for (OrderDetail orderDetail : orderDetailList) {
            //decrease quantity of product
            Product product = productRepository.getById(orderDetail.getProduct().getId());
            product.setCurrentQuantity(product.getCurrentQuantity() - orderDetail.getQuantity());
            productRepository.save(product);
        }

        cart.setCartItem(new HashSet<>());
        cart.setTotalItems(0);
        cart.setTotalPrices(0);
        orderRepository.save(order);
        cartRepository.save(cart);
    }

    @Override
    public void acceptOrder(Long id) {
        Order order = orderRepository.getById(id);
        order.setDeliveryDate(new Date());
        order.setAccepted(true);
        order.setOrderStatus(SHIPPING);
        orderRepository.save(order);
    }

    @Override
    public void cancelOrder(Long id) {
        Order order = orderRepository.getById(id);
        order.setOrderStatus(CANCELED);
        System.out.println("Voduocnay");
        for (OrderDetail orderDetail : order.getOrderDetailList()) {
            //increase quantity of product
//            orderDetail.getProduct().setCurrentQuantity(orderDetail.getProduct().getCurrentQuantity() + orderDetail.getQuantity());
              Product product = productRepository.getById(orderDetail.getProduct().getId());
              System.out.println("sanpham:" + product.getName());
              product.setCurrentQuantity(product.getCurrentQuantity() + orderDetail.getQuantity());
              productRepository.save(product);
        }
        orderRepository.save(order);
//      orderRepository.deleteById(id);
    }


    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
