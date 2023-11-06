package com.ecommerce.admin.controller;

import com.ecommerce.library.dto.AdminDto;
import com.ecommerce.library.model.Admin;
import com.ecommerce.library.model.Notification;
import com.ecommerce.library.service.AdminService;
import com.ecommerce.library.service.NotificationService;
import com.ecommerce.library.service.OrderService;
import com.ecommerce.library.service.RecipeService;
import com.ecommerce.library.service.impl.AdminServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/login")
    public String loginForm(Model model, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            /* Người dùng đã đăng nhập, chuyển hướng đến trang index */
            return "redirect:/index";
        }


        model.addAttribute("title", "Login");
        return "login";
    }


    @RequestMapping(value = {"/index","/"}, method = RequestMethod.GET)
    public String home(Model model,HttpSession session){
        model.addAttribute("title", "Home Page");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof  AnonymousAuthenticationToken){
            return "redirect:/login";
        }

        int unCheckedRecipe = recipeService.numOfUncheckedRecipe();
        int unCheckedOrder = orderService.numOfOrderByStatus("PENDING");
        int todayOrder = orderService.numOfOrderToday();
        int todayRecipe = recipeService.numOfRecipeToday();

        //pie chart
        int unSuccessfulOrder = orderService.numOfOrderByStatus("UNSUCCESSFUL");
        int successfulOrder = orderService.numOfOrderByStatus("SUCCESSFUL");
        int[] orderStatus = {unSuccessfulOrder, successfulOrder};

        //Area chart
        Long[] recipeByMonths = recipeService.numOfRecipeByMonths();

        //Bar chart
        Double[] revenueByMonths = orderService.revenueByMonths();

        List<Notification> notifications = notificationService.getAdminNotifications();

        model.addAttribute("recipeByMonths" , recipeByMonths);
        model.addAttribute("revenueByMonths", revenueByMonths);

        if (notifications == null || notifications.size() == 0) {
            session.setAttribute("checkNotify", "No notification");
            session.removeAttribute("notifications");
        } else {
            session.setAttribute("notifications", notifications);
            session.removeAttribute("checkNotify");
        }

        model.addAttribute("unCheckedRecipe", unCheckedRecipe);
        model.addAttribute("unCheckedOrder", unCheckedOrder);
        model.addAttribute("todayOrder", todayOrder);
        model.addAttribute("todayRecipe", todayRecipe);
        model.addAttribute("orderStatus", orderStatus);

        System.out.println();
        System.out.println("recipe" + recipeByMonths);

        return "index";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("title", "Register");
        model.addAttribute("adminDto", new AdminDto());
        return "register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model){
        model.addAttribute("title", "Forgot Password");
        return "forgot-password";
    }

    @PostMapping("/register-new")
    public String addNewAdmin(@Valid @ModelAttribute("adminDto")AdminDto adminDto,
                              BindingResult result,
                              Model model){

        try {

            if(result.hasErrors()){
                model.addAttribute("adminDto", adminDto);
                result.toString();
                return "register";
            }
            String username = adminDto.getUsername();
            Admin admin = adminService.findByUsername(username);
            if(admin != null){
                model.addAttribute("adminDto", adminDto);
                System.out.println("admin not null");
                model.addAttribute("emailError", "Your email has been registered!");
                return "register";
            }
            if(adminDto.getPassword().equals(adminDto.getRepeatPassword())){
                adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
                adminService.save(adminDto);
                System.out.println("success");
                model.addAttribute("success", "Register successfully!");
                model.addAttribute("adminDto", adminDto);
            }else{
                model.addAttribute("adminDto", adminDto);
                model.addAttribute("passwordError", "Your password maybe wrong! Check again!");
                System.out.println("password not same");
                return "register";
            }
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errors", "The server has been wrong!");
        }
        return "register";

    }

}