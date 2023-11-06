package com.ecommerce.admin.controller;

import com.ecommerce.library.enums.NotificationType;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Order;
import com.ecommerce.library.service.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private VNPayService vnpayService;

    @GetMapping("/orders")
    public String getOrder(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<Order> orders = orderService.findAll();
        model.addAttribute("size", orders.size());
        model.addAttribute("orders", orders);

        return "orders";
    }

    @RequestMapping(value = "/resolve-order/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String resolveOrder(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Order order = orderService.getOrderById(id);
        Customer customer = order.getCustomer();
        NotificationType cancelOrder = NotificationType.CANCEL_ORDER;
        String message = "Your cancel request has been accepted by admin";
        String url = "/order";
        order.setOrderStatus("UNSUCCESSFUL");
//        orderService.deleteOrder(id);
        notificationService.createNotification(cancelOrder.getTitle(), customer, cancelOrder.getType(), message, url);
        return "redirect:/orders";
    }


    @RequestMapping(value = "/accept-order/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String acceptOrder(Principal principal, @PathVariable("id") Long id, RedirectAttributes attributes) {
        if (principal == null) {
            return "redirect:/login";
        }
        Order order = orderService.getOrderById(id);
        Customer customer = order.getCustomer();
        NotificationType acceptOrder = NotificationType.ACCEPT_ORDER;
        String message = "Your order has been accepted and will be delivered soon";
        String url = "/order";
        orderService.acceptOrder(id);
        String username = principal.getName();
        notificationService.createNotification(acceptOrder.getTitle(), customer, acceptOrder.getType(), message, url);

//        List<Order> orderList = customer.getOrders();
        attributes.addFlashAttribute("success", "Accept order successfully!");
        return "redirect:/orders";
    }

    @RequestMapping(value = "/reject-order", method = {RequestMethod.PUT, RequestMethod.GET})
    public String rejectOrder(@RequestParam("orderId") Long id,@RequestParam("reason") String reason, Principal principal, RedirectAttributes attributes) {
        if (principal == null) {
            return "redirect:/login";
        }

        Order order = orderService.getOrderById(id);

        if (order.getPaymentMethod().equals("VNPAY")) {
            int total = (int) order.getTotalPrice();
            String transactionNo = order.getTransactionId();
            String username = "admin " + adminService.findByUsername(principal.getName()).getName();
            vnpayService.refund(total, transactionNo, username);
        }

        Customer customer = order.getCustomer();
        NotificationType acceptOrder = NotificationType.REJECT_ORDER;
        String message = "Your order rejected with reason: " + reason;
        String url = "/order";
        orderService.acceptOrder(id);
        notificationService.createNotification(acceptOrder.getTitle(), customer, acceptOrder.getType(), message, url);

        orderService.rejectOrder(id);

        attributes.addFlashAttribute("success", "Reject order successfully!");
        return "redirect:/orders";
    }


    @RequestMapping(value = "/order-success/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String orderSuccess(Principal principal, @PathVariable("id") Long id, RedirectAttributes attributes) {
        if (principal == null) {
            return "redirect:/login";
        }
        Order order = orderService.getOrderById(id);
        Customer customer = order.getCustomer();
        NotificationType acceptOrder = NotificationType.ORDER_SUCCESS;
        String message = "Thank you for using our service";
        String url = "/order";
        orderService.orderSuccess(id);
        String username = principal.getName();
        notificationService.createNotification(acceptOrder.getTitle(), customer, acceptOrder.getType(), message, url);

//        List<Order> orderList = customer.getOrders();
        attributes.addFlashAttribute("success", "Updated order!");
        return "redirect:/orders";
    }

    @RequestMapping(value = "/order-unsuccess/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String orderUnSuccess(Principal principal, @PathVariable("id") Long id, RedirectAttributes attributes) {
        if (principal == null) {
            return "redirect:/login";
        }
        Order order = orderService.getOrderById(id);

        if (order.getPaymentMethod().equals("VNPAY")) {
            int total = (int) order.getTotalPrice();
            String transactionNo = order.getTransactionId();
            String username = "admin " + adminService.findByUsername(principal.getName()).getName();
            vnpayService.refund(total, transactionNo, username);
        }

        Customer customer = order.getCustomer();
        NotificationType acceptOrder = NotificationType.ORDER_UNSUCCESS;
        String message = "You order was unsuccessful!";
        String url = "/order";
        orderService.orderUnsuccessful(id);
        String username = principal.getName();
        notificationService.createNotification(acceptOrder.getTitle(), customer, acceptOrder.getType(), message, url);

//        List<Order> orderList = customer.getOrders();
        attributes.addFlashAttribute("success", "Updated order!");
        return "redirect:/orders";
    }


}
