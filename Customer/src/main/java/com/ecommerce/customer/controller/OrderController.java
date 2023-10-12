package com.ecommerce.customer.controller;

import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Order;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.OrderService;
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
    CustomerService customerService;

    @Autowired
    OrderService orderService;

    @GetMapping("/check-out")
    public String checkout(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        if (customer.getPhoneNumber().trim().isEmpty() || customer.getAddress() == null || customer.getAddress().trim().isEmpty()) {
            model.addAttribute("customer", customer);
            model.addAttribute("error", "You must fill information before checkout");
            return "account";
        } else {
            model.addAttribute("customer", customer);
            ShoppingCart cart = customer.getShoppingCart();
            model.addAttribute("cart", cart);
        }

        return "checkout";
    }


    @GetMapping("/order")
    public String order(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        List<Order> orderList = customer.getOrders();
        model.addAttribute("orders", orderList);
        return "order";
    }

    @PostMapping("/save-order")
    public String saveOrder(Principal principal,
                            @RequestParam("province") String province,
                            @RequestParam("district") String district,
                            @RequestParam("ward") String ward) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        ShoppingCart cart = customer.getShoppingCart();
        String shippingAddress = province + ", " + district + ", " + ward + "," + customer.getAddress();
        System.out.println("diachi:" + shippingAddress);
        orderService.saveOrder(cart, shippingAddress);
        return "redirect:/order";
    }

//    @GetMapping(value = "/cancel-order/{id}")
//    public String cancelOrder(@PathVariable Long id, Principal principal, Model model) {
//        if(principal == null){
//            return "redirect:/login";
//        }
//        orderService.cancelOrder(id);
//        String username = principal.getName();
//        Customer customer = customerService.findByUsername(username);
//        List<Order> orderList = customer.getOrders();
//        model.addAttribute("orders", orderList);
//        return "redirect:/order";
//    }

    @RequestMapping(value = "/cancel-order/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String cancelOrder(Principal principal,@PathVariable("id") Long id, RedirectAttributes attributes)  {
        if (principal == null) {
            return "redirect:/login";
        }
        orderService.cancelOrder(id);
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        List<Order> orderList = customer.getOrders();
        attributes.addFlashAttribute("success", "Cancel order successfully!");
        return "redirect:/order";
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
        attributes.addFlashAttribute("success", "Cancel order successfully!");
        return "redirect:/order";
    }

}
