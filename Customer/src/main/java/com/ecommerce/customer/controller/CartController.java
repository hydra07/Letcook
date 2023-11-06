package com.ecommerce.customer.controller;

import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class CartController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private ProductService productService;

    @GetMapping("/cart")
    public String cart(Model model, Principal principal, HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getShoppingCart();
        if (shoppingCart == null || shoppingCart.getTotalItems() == 0) {
            model.addAttribute("check", "No item in your cart");
        } else {
            session.setAttribute("totalItems", shoppingCart.getTotalItems());
            model.addAttribute("subTotal", shoppingCart.getTotalPrices());
            model.addAttribute("shoppingCart", shoppingCart);
        }
        return "cart";
    }

    @PostMapping("/add-to-cart")
    public String addItemToCart(Model model,
                                @RequestParam("id") Long id,
                                @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
                                Principal principal,
                                HttpServletRequest request,
                                HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        }
        Product product = productService.getProductById(id);
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);

        ShoppingCart shoppingCart = cartService.addItemToCart(product, quantity, customer);
        session.setAttribute("totalItems", shoppingCart.getTotalItems());
        return "redirect:" + request.getHeader( "Referer");
    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=update")
    public String updateCart(@RequestParam("id") Long productId,
                             @RequestParam("quantity") int quantity,
                             Model model,
                             Principal principal,
                             RedirectAttributes attributes) {
        if (principal == null) {
            return "redirect:/login";
        } else {

            String username = principal.getName();
            Customer customer = customerService.findByUsername(username);
            Product product = productService.getProductById(productId);
            ShoppingCart cart;
            if (quantity <= 0) {
                cart = cartService.deleteItemFromCart(product, customer);
                return "redirect:/cart";
            } else {
                if (quantity > product.getCurrentQuantity()) {
                    attributes.addFlashAttribute("notify", "Quantity is not enough");
                    cart = customer.getShoppingCart();
                } else {
                    cart = cartService.updateItemInCart(product, quantity, customer);
                }
            }
            return "redirect:/cart";
        }
    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=delete")
    public String deleteItemFromCart(@RequestParam("id") Long productId,
                                     Model model,
                                     Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            String usernme = principal.getName();
            Customer customer = customerService.findByUsername(usernme);
            Product product = productService.getProductById(productId);
            ShoppingCart cart = cartService.deleteItemFromCart(product, customer);
            return "redirect:/cart";
        }
    }


    @GetMapping("/address")
    public String getAddress() {
        return "address";
    }

    @PostMapping("/processAddress")
    public String printAddress(@RequestParam("province") String province,
                               @RequestParam("district") String district,
                               @RequestParam("ward") String ward) {
        System.out.println("diachi:" + province + " " + district + " " + ward);
        return "redirect:/address";
    }
}
