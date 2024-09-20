package com.dxc.accountservice.configuration;

import com.dxc.accountservice.jwt.JwtTokenFilter;
import com.dxc.accountservice.persistence.entity.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

//@TestConfiguration
public class ApplicationSecurity {

//    @Autowired
//    private JwtTokenFilter jwtTokenFilter;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public DaoAuthenticationProvider authProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(
//            AuthenticationConfiguration authConfig
//    ) throws Exception {
////        logger.info("Entra authenticationManager!!!!");
//        return authConfig.getAuthenticationManager();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable())
//                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        // http.authenticationProvider(authProvider()); // can be commented
//
//        http
//                .authorizeHttpRequests((requests) -> requests
//                                .antMatchers("/auth/login",
//                                        "/docs/**",
//                                        "/h2-ui/**",
//                                        "/configuration/ui",
//                                        "/swagger-resources/**",
//                                        "/configuration/security",
//                                        "/swagger-ui/**",
//                                        "/configuration/ui/auth-user",
//                                        "/v2/api-docs/**",
//                                        "/v3/api-docs/**",
//                                        "/webjars/**"
//                                ).permitAll()
//                        .antMatchers(HttpMethod.POST,"/account/**").hasAuthority(RoleEnum.Director.name())
//                                .antMatchers(HttpMethod.PUT,"/account/**").hasAuthority(RoleEnum.Director.name())
//                                .antMatchers(HttpMethod.DELETE,"/account/**").hasAuthority(RoleEnum.Director.name())
//                                .antMatchers(HttpMethod.GET, "/products/**").hasAnyAuthority(RoleEnum.Cashier.name(), RoleEnum.Director.name())
//                                .anyRequest().authenticated()
//                );
//
//        http.headers(headers ->
//                headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin())
//        );
//
//        http.exceptionHandling((exception) -> exception.authenticationEntryPoint(
//                (request, response, ex) -> {
//                    response.sendError(
//                            HttpServletResponse.SC_UNAUTHORIZED,
//                            ex.getMessage()
//                    );
//                }
//        ));
//
//        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
}
