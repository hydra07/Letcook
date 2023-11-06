package com.ecommerce.library.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    @Size(min = 3, max = 15, message = "First name should have 3-15 characters")
    private String firstName;
    @Size(min = 3, max = 15, message = "Last name should have 3-15 characters")
    private String lastName;
    private String username;
    @Size(min = 5, max = 20, message = "Password should have 5-20 characters")
    private String password;
    private String repeatPassword;
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number! (10 digits)")
    private String phoneNumber;
    private String address;

//    private boolean isVerified;

    private String getFullName() {
        return firstName + " " + lastName;
    }
}
