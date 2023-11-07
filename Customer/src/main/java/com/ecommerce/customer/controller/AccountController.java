package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.City;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.utils.ImageUpload;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.PrincipalMethodArgumentResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.font.ImageGraphicAttribute;
import java.security.Principal;
import java.util.List;

@Controller
public class AccountController {

    @Autowired
    CustomerService customerService;

    @Controller
    public class GetUserWithPrincipalController {

        @RequestMapping(value = "/username", method = RequestMethod.GET)
        @ResponseBody
        public String currentUserName(Principal principal) {
            return principal.getName();
        }
    }

    @GetMapping("/profile")
    public String myProfile(Principal principal, Model model) {
        if(principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
//        String avatar = ;
        model.addAttribute("customer", customer);
//        model.addAttribute()
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
//                                 @RequestParam("image") MultipartFile image,
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

    @PostMapping("/update-avatar")
    public String updateAvatar(@RequestParam("image") MultipartFile image, Principal principal, Model model){
        ImageUpload imageUpload = new ImageUpload();
//        String avatar = imageUpload.getURL(imageUpload.uploadImage(image, "avatar"), "avatar");
        String avatar = "api/images/avatar/"+imageUpload.uploadImage(image, "avatar");
        Customer customer = customerService.updateAvatar(principal.getName(), avatar);
//        String avatar = customerService.updateAvatar(image, principal.getName());
        model.addAttribute("customer", customer);
        return "my-account";
    }
}
