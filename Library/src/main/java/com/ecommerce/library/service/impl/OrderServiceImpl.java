package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.*;
import com.ecommerce.library.repository.*;
import com.ecommerce.library.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {
    static final String PENDING = "PENDING";
    static final String SHIPPING = "SHIPPING";
    static final String CANCELED = "CANCELED";
    static final String REJECTED = "REJECTED";
    static final String SUCCESSFUL = "SUCCESSFUL";
    static final String UNSUCCESSFUL = "UNSUCCESSFUL";


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
    public void saveOrder(ShoppingCart cart, String shippingAddress, String paymentMethod, String transactionNo) {
        Order order = new Order();
        order.setOrderStatus(PENDING);
        order.setOrderDate((new Date()));
        order.setCustomer(cart.getCustomer());
        order.setTotalPrice(cart.getTotalPrices());
        order.setShippingAddress(shippingAddress);
        order.setPaymentMethod(paymentMethod);
        order.setAccepted(false);
        order.setTransactionId(transactionNo);
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
//        order.setDeliveryDate(new Date());
        order.setAccepted(true);
        order.setOrderStatus(SHIPPING);
        orderRepository.save(order);
    }

    @Override
    public void rejectOrder(Long id) {
        Order order = orderRepository.getById(id);
        order.setOrderStatus(REJECTED);
        for (OrderDetail orderDetail : order.getOrderDetailList()) {
            Product product = productRepository.getById(orderDetail.getProduct().getId());
            product.setCurrentQuantity(product.getCurrentQuantity() + orderDetail.getQuantity());
            productRepository.save(product);
        }
        orderRepository.save(order);
    }

    @Override
    public void orderSuccess(Long id) {
        Order order = orderRepository.getById(id);
        order.setDeliveryDate(new Date());
        order.setAccepted(true);
        order.setOrderStatus(SUCCESSFUL);
        orderRepository.save(order);
    }

    @Override
    public void orderUnsuccessful(Long id) {
        Order order = orderRepository.getById(id);
        order.setOrderStatus(UNSUCCESSFUL);
        for (OrderDetail orderDetail : order.getOrderDetailList()) {
            Product product = productRepository.getById(orderDetail.getProduct().getId());
            product.setCurrentQuantity(product.getCurrentQuantity() + orderDetail.getQuantity());
            productRepository.save(product);
        }
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

    @Override
    public int numOfOrderToday() {
        return orderRepository.countByTodayOrder();
    }

    @Override
    public Double[] revenueByMonths() {
        List<Double> list = orderRepository.getRevenueByMonths();
        Double[] revenueByMonths = list.toArray(new Double[0]);
        return revenueByMonths;
    }

    @Override
    public List<Order> getUnCheckedOrder() {
        return null;
    }


    @Override
    public int numOfOrderByStatus(String status) {
        return orderRepository.countByOrderStatus(status);
    }
}
