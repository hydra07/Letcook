package com.ecommerce.library.service;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Customer;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
    Customer findByUsername(String username);
    Customer save(CustomerDto customerDto);
}
