package com.ecommerce.admin.controller;

import com.ecommerce.library.enums.NotificationType;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Order;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.NotificationService;
import com.ecommerce.library.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @GetMapping("/orders")
    public String getOrder(Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        List<Order> orders = orderService.findAll();
        model.addAttribute("size" , orders.size());
        model.addAttribute("orders", orders);

        return "orders";
    }

    @RequestMapping(value = "/resolve-order/{id}", method = {RequestMethod.PUT , RequestMethod.GET})
    public String resolveOrder(@PathVariable("id") Long id, Model model, Principal principal){
        if(principal == null){
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
    public String acceptOrder(Principal principal,@PathVariable("id") Long id, RedirectAttributes attributes)  {
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
}
