/*
package com.golamyusuf.demo.services;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Implement user lookup logic here
        // For example, look up the user in the database
        // For demo purposes, we're returning a hardcoded user
        if ("admin".equals(username)) {
            return User.withUsername("admin")
                    .password("{noop}admin") // {noop} means no password encoding
                    .roles("ADMIN")
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}

*/
