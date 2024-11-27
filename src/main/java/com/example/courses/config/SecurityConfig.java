package com.example.courses.config;

import com.example.courses.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll();
                    auth.requestMatchers("/").permitAll();
                    /* auth.requestMatchers("/user").hasRole("USER");*/
                    auth.requestMatchers("/admin").hasRole("Admin");
                    auth.requestMatchers("/usuarios/**").permitAll();
                    auth.requestMatchers("/courses/**").permitAll();
                    auth.requestMatchers("/inscripciones/**").permitAll();
                    auth.requestMatchers("/verify-session/").permitAll();
                })
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService) // Configura el CustomUserDetailsService
                .passwordEncoder(passwordEncoder()) // Configura el PasswordEncoder
                .and()
                .build();
    }
}

