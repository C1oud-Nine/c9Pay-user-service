package com.c9Pay.userservice.config;

import com.c9Pay.userservice.jwt.JwtFilter;
import com.c9Pay.userservice.jwt.JwtSecurityConfig;
import com.c9Pay.userservice.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.beans.JavaBean;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, TokenProvider tokenProvider) throws Exception {
        return http
                .sessionManagement(s -> s.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests((auth) -> auth.requestMatchers("/user-service/test").hasRole("USER"))
                .authorizeHttpRequests((auth) -> auth.requestMatchers("/**").permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public TokenProvider tokenProvider(@Value("${jwt.secret}") String secret,
                                       @Value("%{jwt.service-type}") String serviceType,
                                       @Value("${jwt.time}") long tokenValidityInSeconds) {
        return new TokenProvider(secret, serviceType, tokenValidityInSeconds);
    }
}
