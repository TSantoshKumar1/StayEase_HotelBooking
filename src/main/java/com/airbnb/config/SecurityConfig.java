package com.airbnb.config;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;


import java.net.http.HttpRequest;
import java.util.List;

@Configuration
public class SecurityConfig {

    private JWTRequestFilter jwtRequestFilter;

    public SecurityConfig(JWTRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

         http.csrf(csrf -> csrf.disable()).
                cors(cors -> cors.disable()).
                 addFilterBefore(jwtRequestFilter,AuthorizationFilter.class).
               //  authorizeHttpRequests(auth-> auth.anyRequest().permitAll());
        authorizeHttpRequests(auth->auth.requestMatchers("/api/v1/users/addUser","/api/v1/users/loginUser").permitAll()
              .requestMatchers("/api/v1/countries/addCountry").hasRole("ADMIN").
                requestMatchers("/api/v1/users/profile").hasAnyRole("ADMIN","USER")
               .requestMatchers("/api/v1/reviews/addReview").hasRole("USER")
                  .anyRequest().authenticated());

       return http.build();


    }
}


