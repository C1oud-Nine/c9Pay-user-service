package com.c9Pay.userservice.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.Clock;
import java.util.List;
import java.time.Instant;
import java.util.Base64;

@Slf4j
public class TokenProvider {

    private final String serviceType;

    private final long tokenValidateInSeconds;

    private Algorithm algorithm;

    private final String SERVICE_TYPE= "type";



    public TokenProvider(@Value("${jwt.secret}") String secret,
                         @Value("%{jwt.service-type") String serviceType,
                         @Value("${jwt.time}") long tokenValidateTime){
        this.serviceType = serviceType;
        this.tokenValidateInSeconds = tokenValidateTime;
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        this.algorithm = Algorithm.HMAC512(keyBytes);
    }

    public String createToken(Authentication authentication){
        return JWT.create()
                .withSubject(authentication.getName())
                .withClaim(SERVICE_TYPE, serviceType)
                .withExpiresAt(Instant.ofEpochMilli(System.currentTimeMillis() + tokenValidateInSeconds * 1000))
                .sign(algorithm);
    }

    public Authentication getAuthentication(String token){
        DecodedJWT decodedJWT=  JWT.decode(token);
        String sub = decodedJWT.getSubject();
        String type = decodedJWT.getClaim(SERVICE_TYPE).as(String.class);
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(type), new SimpleGrantedAuthority("ROLE_USER"));
        User principle = new User(sub, "", authorities);
        return new UsernamePasswordAuthenticationToken(principle, token, authorities);
    }

    public boolean validateToken(String token, HttpServletRequest request){
        try{
            JWTVerifier.BaseVerification verification = (JWTVerifier.BaseVerification) JWT.require(algorithm)
                    .withClaimPresence(SERVICE_TYPE)
                    .acceptExpiresAt(tokenValidateInSeconds);
            JWTVerifier verifier = verification.build(Clock.systemUTC());
            verifier.verify(token);
            return true;
        }catch (JWTVerificationException e){
            log.debug("auth fail reason: {}", e.getMessage());
            return false;
        }
    }
}
