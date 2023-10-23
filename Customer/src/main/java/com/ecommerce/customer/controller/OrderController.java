package com.ecommerce.customer.controller;

import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Order;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.OrderService;
import com.ecommerce.library.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

    @Autowired
    VNPayService vnPayService;

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
                            @RequestParam("ward") String ward,
                            @RequestParam("paymentMethod") String paymentMethod,
                            HttpServletRequest request, HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        ShoppingCart cart = customer.getShoppingCart();
        String shippingAddress = province + ", " + district + ", " + ward + "," + customer.getAddress();
        System.out.println("diachi:" + shippingAddress);
        if (paymentMethod.equals("BANKING")) {
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/shop";
            System.out.println("baseUrl: " + baseUrl);
            String orderInfo = "Thanh toan don hang-" + customer.getName();
            String vnpayUrl = vnPayService.createOrder((int) cart.getTotalPrices(), orderInfo, baseUrl);
            session.setAttribute("shippingAddress", shippingAddress);
//            orderService.saveOrder(cart, shippingAddress, paymentMethod);
            return "redirect:" + vnpayUrl;
        }
        orderService.saveOrder(cart, shippingAddress, paymentMethod);
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
    public String cancelOrder(Principal principal, @PathVariable("id") Long id, RedirectAttributes attributes) {
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
    public String acceptOrder(Principal principal, @PathVariable("id") Long id, RedirectAttributes attributes) {
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

    @GetMapping("/vnPayPayment")
    public String completeOrder(HttpServletRequest request, Principal principal,HttpSession session){
        int paymentStatus = vnPayService.orderReturn(request);
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
//        String paymentMethod = request.getParameter("vnp_PaymentMethod");
//        String shippingAddress = request.getParameter("vnp_Address");
        String[] lists = orderInfo.split("-");
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        ShoppingCart cart = customer.getShoppingCart();
        if (paymentStatus == 1) {
            String shippingAddress = (String) session.getAttribute("shippingAddress");
            orderService.saveOrder(cart, shippingAddress, "BANKING");
            session.removeAttribute("shippingAddress");
            return "redirect:/order";
        }

        return "redirect:/check-out";
    }

}
