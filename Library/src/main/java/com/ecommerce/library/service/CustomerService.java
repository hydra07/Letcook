package com.ecommerce.library.service;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Customer;
import org.hibernate.id.uuid.CustomVersionOneStrategy;

public interface CustomerService{
    Customer save(Customer customer);
    Customer save(CustomerDto customerDto);
    Customer findByUsername(String username);

    Customer findByPhoneNumber(String phoneNumber);

    public CustomerDto getCustomer(String username);

    public Customer update(CustomerDto dto);

    public Customer updateAvatar(String username, String avatar);


    public Customer findById(Long id);

    public Customer addToFavourite(Long id , String username);

    public Customer removeFromFavorite(Long id, String username);

    public boolean isFavorite(Long id, String username);

}
