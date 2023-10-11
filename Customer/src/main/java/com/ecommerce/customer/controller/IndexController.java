package com.ecommerce.customer.controller;


import org.springframework.ui.Model;
import com.ecommerce.library.dto.CustomerDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class IndexController {
//    @GetMapping("/")
//    public String index(Model model, Principal principal) {
//        if (principal != null) {
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            if (auth.getPrincipal() instanceof CustomerDto customerDto) {
//                model.addAttribute("customerDto", customerDto);
//            }  // Xử lý trường hợp không phải CustomerDto
//
//        } else {
//            model.addAttribute("customerDto", null);
//        }
//
//        return "index";
//    }


}
