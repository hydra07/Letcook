package com.ecommerce.library.dto;

import com.ecommerce.library.model.Comment;
import com.ecommerce.library.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReactionDto {
    private Long id;
    private String type;
    private Comment comment;
    private Set<Customer> customers;
}
