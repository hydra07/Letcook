package com.ecommerce.library.service;


import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Notification;

public interface NotificationService {
    Notification createNotification(String title, Customer customer, String type,String message, String url);
    Notification getById(Long id);

    void deleteNotificationById(Long id);

}
