package com.ecommerce.library.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    private Date orderDate;
    private Date deliveryDate;
    private double totalPrice;
    private double shippingFee;
    private String orderStatus;
    private String notes;

    @Column(nullable = true)
    private String transactionId;

    @Column(columnDefinition = "NVARCHAR(100)")
    private String shippingAddress;

    private boolean isAccepted;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    private String paymentMethod;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", orphanRemoval = true)
    private List<OrderDetail> orderDetailList;
}