package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.repository.CustomerRepository;
import com.ecommerce.library.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    @Autowired
    CustomerService customerService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("page", "Register");
        model.addAttribute("customerDto", new CustomerDto());
        return "register";
    }

    @PostMapping("/do-register")
    public String registerCustomer(@Valid @ModelAttribute("customerDto") CustomerDto customerDto,
                                   BindingResult result,
                                   Model model) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("customerDto", customerDto);
                return "register";
            }
            String username = customerDto.getUsername();
            Customer customer = customerService.findByUsername(username);
            if (customer != null) {
                model.addAttribute("customerDto", customerDto);
                model.addAttribute("error", "Email has been register!");
                return "register";
            }
            //check phone is exist
            Customer customerPhone = customerService.findByPhoneNumber(customerDto.getPhoneNumber());
            if (customerPhone != null) {
                model.addAttribute("customerDto", customerDto);
                model.addAttribute("error", "Phone number has been register!");
                return "register";
            }
            if (customerDto.getPassword().equals(customerDto.getRepeatPassword())) {
                customerDto.setPassword(passwordEncoder.encode(customerDto.getPassword()));
                customerService.save(customerDto);
                model.addAttribute("success", "Register successfully!");
            } else {
                model.addAttribute("error", "Password is incorrect");
                model.addAttribute("customerDto", customerDto);
                return "register";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Server is error, try again later!");
        }
        return "register";
    }
}
