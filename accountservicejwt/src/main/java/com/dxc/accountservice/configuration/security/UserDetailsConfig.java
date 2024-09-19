package com.dxc.accountservice.configuration.security;

import com.dxc.accountservice.persistence.entity.RoleEnum;
import com.dxc.accountservice.persistence.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

@Configuration
public class UserDetailsConfig {

    private RoleEnum role;
    private List<User> users = List.of(
            new User(1,"user1","password",role.Cashier),
            new User(2,"user2","password",role.Director),
            new User(3,"user3","password",role.Director),
            new User(4,"user4","password",role.Cashier),
            new User(5,"user5","password",role.Director)
    );

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return users.stream()
                        .filter(user -> user.getUsername().equals(username))
                        .findFirst().orElseThrow(()->new UsernameNotFoundException( "User " + username + " not found"));
            }
        };
    }
}
