package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.City;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class AccountController {

    @Autowired
    CustomerService customerService;


    @GetMapping("/profile")
    public String myProfile(Principal principal) {
        return  "my-account";
    }

    @GetMapping("/account")
    public String accountHome(Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }

        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);

        model.addAttribute("customer", customer);


        return "account";
    }

    @PostMapping("/update-profile")
    public String updateCustomer(@Valid @ModelAttribute("customer") CustomerDto customerDto,
                                 BindingResult result,
                                 RedirectAttributes attributes,
                                 Model model,
                                 Principal principal){
        System.out.println("cacc");
        if(principal == null){
            return "redirect:/login";
        }else{
            String username = principal.getName();
            CustomerDto customer = customerService.getCustomer(username);
            System.out.println("ten:" + customer.getFirstName());
            if(result.hasErrors()){
                System.out.println("coloi");
                List<ObjectError> errors = result.getAllErrors();
                for (ObjectError error : errors) {
                    System.out.println(error.getDefaultMessage()); // In ra thông báo lỗi cụ thể
                }

                return "account";
            }
            customerService.update(customerDto);
            CustomerDto customerUpdate = customerService.getCustomer(principal.getName());
            model.addAttribute("success", "Update successfully!");
            model.addAttribute("customer", customerUpdate);
            return "account";
        }
    }
}
