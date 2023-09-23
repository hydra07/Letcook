package com.example.swp.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class OrderDetailId implements Serializable {
  @Column(name = "OrderId")
  private Long orderId;

  @Column(name = "ProductId")
  private Long productId;
}
