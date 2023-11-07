package com.ecommerce.admin.controller;

import com.ecommerce.library.model.OrderDetail;
import com.ecommerce.library.service.OrderDetailService;
import com.ecommerce.library.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/order-detail/{id}")
    public String getOrderDetail(Principal principal, @PathVariable("id") Long id, Model model) {

        if(principal == null) {
            return "redirect:/login";
        }
        List<OrderDetail> detailList = orderService.getOrderById(id).getOrderDetailList();
        model.addAttribute("orderDetailList", detailList);
        return "order-detail";
    }
}
