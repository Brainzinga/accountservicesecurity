package com.dxc.accountservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class ResourceServerConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/account/**").authenticated()
                .mvcMatchers("/account/**").hasAuthority("SCOPE_account.read")
                .and()
                .oauth2ResourceServer()
                .jwt();
        return http.build();
    }
}