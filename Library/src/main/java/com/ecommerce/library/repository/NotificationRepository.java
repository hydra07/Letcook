package com.ecommerce.library.repository;

import com.ecommerce.library.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(value = "SELECT * FROM Notification WHERE customer_id IS NULL", nativeQuery = true)
    List<Notification> findNotificationByCustomerIsNull();

}
