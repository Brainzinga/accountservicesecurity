package com.dxc.accountservice.configuration.security;

import com.dxc.accountservice.persistence.entity.RoleEnum;
import com.dxc.accountservice.persistence.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class UserDetailsConfig {

    private RoleEnum role;
//    @Autowired
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private List<User> users;

    @Bean
    public UserDetailsService userDetailsService() {
        users = List.of(
                new User(1, "user", passwordEncoder.encode("password"), role.Cashier),
                new User(2, "admin", passwordEncoder.encode("password"), role.Director)
        );
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return users.stream()
                        .filter(user -> user.getUsername().equals(username))
                        .findFirst().orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
            }
        };
    }
}
