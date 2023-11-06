package com.ecommerce.library.service;

import jakarta.servlet.http.HttpServletRequest;

public interface VNPayService {
    String createOrder(int total, String orderInfor, String urlReturn);
    int orderReturn(HttpServletRequest request);
    public void refund(int total, String transactionId, String username);
}