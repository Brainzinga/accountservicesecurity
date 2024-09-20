package com.dxc.accountservice.configuration;

import com.dxc.accountservice.jwt.JwtTokenFilter;
import com.dxc.accountservice.jwt.JwtTokenUtil;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@TestConfiguration
public class ApplicationSecurity {

    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtTokenFilter jwtTokenFilter;


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)throws Exception{
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        http.csrf(csrf -> csrf.disable()).sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(request -> request.antMatchers(
                "/auth/login","/docs/**","/users/**","/h2-ui/**","/configuration/ui","/swagger-resources/**",
                "/configuration/security","/swagger-ui.html","/webjars/**"
        ).permitAll()
                        .antMatchers("/account/**").permitAll()
                        .anyRequest().authenticated()
        );
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
