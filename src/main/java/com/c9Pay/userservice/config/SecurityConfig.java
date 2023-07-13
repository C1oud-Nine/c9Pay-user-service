package com.c9Pay.userservice.config;

import com.c9Pay.userservice.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import java.beans.JavaBean;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, TokenProvider tokenProvider) throws Exception {
        return http
                .sessionManagement(s -> s.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests((auth) -> auth.requestMatchers("/**").permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }


    @Bean
    public TokenProvider tokenProvider(@Value("${jwt.secret}") String secret,
                                       @Value("%{jwt.service-type}") String serviceType,
                                       @Value("${jwt.time}") long tokenValidityInSeconds) {
        return new TokenProvider(secret, serviceType, tokenValidityInSeconds);
    }
}
