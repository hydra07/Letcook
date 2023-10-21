package com.ecommerce.admin.controller;

import com.ecommerce.library.model.Order;
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
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }

    @RequestMapping(value = "/accept-order/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String acceptOrder(Principal principal,@PathVariable("id") Long id, RedirectAttributes attributes)  {
        if (principal == null) {
            return "redirect:/login";
        }
        orderService.acceptOrder(id);
        String username = principal.getName();
//        Customer customer = customerService.findByUsername(username);
//        List<Order> orderList = customer.getOrders();
        attributes.addFlashAttribute("success", "Accept order successfully!");
        return "redirect:/orders";
    }
}
