package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Notification;
import com.ecommerce.library.repository.NotificationRepository;
import com.ecommerce.library.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {


    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification createNotification(String title, Customer customer, String type, String message, String url) {
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setCustomer(customer);
        notification.setType(type);
        notification.setMessage(message);
        notification.setUrl(url);
        notification.set_read(false);
        notification.setCreateAt(new Date());

        return notificationRepository.save(notification);
    }

    @Override
    public Notification getById(Long id) {
        return notificationRepository.getById(id);
    }

    @Override
    public void deleteNotificationById(Long id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public List<Notification> getAdminNotifications() {
        return notificationRepository.findNotificationByCustomerIsNull();
    }
}
