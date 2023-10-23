package com.ecommerce.customer.controller;

import com.ecommerce.library.model.Customer;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.FollowService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class FollowController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private FollowService followService;

    @RequestMapping(value = "/follow/{followingName}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String follow(Model model, Principal principal,
                         HttpServletRequest request,
                         @PathVariable("followingName") String followingUserName
    ) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        Customer follower = customerService.findByUsername(username);
        Customer following = customerService.findByUsername(followingUserName);
        followService.follow(follower, following);
        return "redirect:" + request.getHeader("Referer");
    }

    @RequestMapping(value = "/unFollow/{followingName}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String unFollow(Model model, Principal principal,
                         HttpServletRequest request,
                         @PathVariable("followingName") String followingUserName
    ) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        Customer follower = customerService.findByUsername(username);
        Customer following = customerService.findByUsername(followingUserName);
        followService.unFollow(follower, following);
        return "redirect:" + request.getHeader("Referer");
    }
}
