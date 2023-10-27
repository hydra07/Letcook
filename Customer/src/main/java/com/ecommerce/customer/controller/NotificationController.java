package com.ecommerce.customer.controller;

import com.ecommerce.library.model.Notification;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.NotificationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
public class NotificationController {

    @Autowired
    CustomerService customerService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/handle-notification/{id}")
    public String handleNotification(@PathVariable("id") Long id,
                                     HttpSession session, Principal principal, Model model) {

        Notification notification = notificationService.getById(id);
        String url = notification.getUrl();
        notificationService.deleteNotificationById(id);
        String userName = principal.getName();
        List<Notification> notifications = customerService.findByUsername(userName).getNotifications();
        if(notifications == null || notifications.size() == 0){
            session.setAttribute("notifyCheck", "No notification");
            session.removeAttribute("notifications");
        }else {
            session.removeAttribute("checkNotify");
            session.setAttribute("notifications", notifications);
        }
        return "redirect:" + url;
    }
}
