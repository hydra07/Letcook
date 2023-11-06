package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Recipe;
import com.ecommerce.library.repository.CustomerRepository;
import com.ecommerce.library.repository.RecipeRepository;
import com.ecommerce.library.repository.RoleRepository;
import com.ecommerce.library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RecipeRepository recipeRepository;

    @Override
    public Customer save(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setPassword(customerDto.getPassword());
        customer.setUsername(customerDto.getUsername());
        customer.setRoles(Arrays.asList(roleRepository.findByName("CUSTOMER")));
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        return customerRepository.save(customer);
    }


    @Override
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public Customer findByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber);
    }
    private CustomerDto mapperDTO(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setUsername(customer.getUsername());
        customerDto.setPassword(customer.getPassword());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        return customerDto;
    }

    @Override
    public CustomerDto getCustomer(String username) {
        CustomerDto customerDto = new CustomerDto();
        Customer customer = customerRepository.findByUsername(username);
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setUsername(customer.getUsername());
        customerDto.setPassword(customer.getPassword());
        customerDto.setAddress(customer.getAddress());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        System.out.println("layCustomer");
        return customerDto;
    }

    @Override
    public Customer update(CustomerDto dto) {
        Customer customer = customerRepository.findByUsername(dto.getUsername());
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setAddress(dto.getAddress());
        customer.setPhoneNumber(dto.getPhoneNumber());
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateAvatar(String username, String avatar) {
        Customer customer = customerRepository.findByUsername(username);
        customer.setImage(avatar);
        return customerRepository.save(customer);
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Customer addToFavourite(Long id, String username) {
        Customer customer = customerRepository.findByUsername(username);
        Recipe recipe = recipeRepository.getById(id);

        if (customer.getFavoriteRecipes().contains(recipe)) {
            return null;
        }

        customer.getFavoriteRecipes().add(recipe);
        return customerRepository.save(customer);
    }

    @Override
    public Customer removeFromFavorite(Long id, String username) {
        Customer customer = customerRepository.findByUsername(username);
        Recipe recipe = recipeRepository.getById(id);

        if (!customer.getFavoriteRecipes().contains(recipe)) {
            return null;
        }

        customer.getFavoriteRecipes().remove(recipe);
        return customerRepository.save(customer);
    }

    @Override
    public boolean isFavorite(Long id, String username) {
        Customer customer = customerRepository.findByUsername(username);
        Recipe recipe = recipeRepository.getById(id);
        if(customer.getFavoriteRecipes().contains(recipe)){
            return true;
        }
        return false;

    }
    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

}
