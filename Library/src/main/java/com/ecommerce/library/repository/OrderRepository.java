package com.ecommerce.library.repository;

import com.ecommerce.library.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>{
    Order findTopByOrderByIdDesc();

    @Query(value = "SELECT COALESCE(COUNT(order_id),0) FROM orders WHERE CAST(order_date AS DATE) = CAST(GETDATE() AS DATE)", nativeQuery = true)
    int countByTodayOrder();

    @Query(value = "SELECT COALESCE(COUNT(order_id),0) FROM orders WHERE order_status = :orderStatus", nativeQuery = true)
    int countByOrderStatus(@Param("orderStatus") String orderStatus);

    @Query(
            value = "SELECT COALESCE(SUM(orders.total_price), 0) AS TotalPrice " +
                    "FROM Months " +
                    "LEFT JOIN orders ON Months.MonthID = MONTH(orders.order_date) " +
                    "AND YEAR(orders.order_date) = YEAR(GETDATE()) AND orders.order_status = 'SUCCESSFUL'" +
                    "GROUP BY Months.MonthID;",
            nativeQuery = true
    )
    List<Double> getRevenueByMonths();

}
