package com.dxc.accountservice.configuration;

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
            new User("user1","password",role.Cashier),
            new User("user2","password",role.Director),
            new User("user3","password",role.Director),
            new User("user4","password",role.Cashier),
            new User("user5","password",role.Director)
    );

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return users.stream().filter(user -> user.getUsername().equals(username)).findFirst().get();
            }
        };
    }
}
