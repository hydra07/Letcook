package com.ecommerce.customer.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomCustomer extends User {

    private boolean isEnabled;

    public CustomCustomer(String username, String password, boolean enabled, boolean accountNonExpired,
                          boolean credentialsNonExpired, boolean accountNonLocked,
                          Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.isEnabled = enabled;
    }

    public boolean isEnabled() {
        return isEnabled;
    }
}