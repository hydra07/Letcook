package com.ecommerce.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Collection;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers", uniqueConstraints = @UniqueConstraint(columnNames = {"phone_number"}))
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(columnDefinition = "NVARCHAR(50)")
    @Size(min = 3, max = 15, message = "First name should have 3-15 characters")
    private String firstName;

    @Column(columnDefinition = "NVARCHAR(50)")
    @Size(min = 3, max = 15, message = "Last name should have 3-15 characters")
    private String lastName;

    @Column(columnDefinition = "NVARCHAR(50)")
    private String username;

    @Column(columnDefinition = "NVARCHAR(50)")
    private String country;
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(columnDefinition = "NVARCHAR(100)")
    private String address;

    private String password;


    private String image;

    @Column(name = "city",columnDefinition = "NVARCHAR(50)")
    private String city;

    @OneToOne(mappedBy = "customer")
    private ShoppingCart shoppingCart;
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable( name = "customers_roles",
            joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Collection<Role> roles;

}
