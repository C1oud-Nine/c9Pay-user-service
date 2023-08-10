package com.c9Pay.userservice.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

import static com.c9Pay.userservice.constant.BearerConstant.BEARER_PREFIX;
import static com.c9Pay.userservice.constant.CookieConstant.AUTHORIZATION_HEADER;

@Slf4j
@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.time}")
    private long EXPIRATION_TIME;

    private Key key;

    @PostConstruct
    public void createSecureKey(){
        key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
    public String getToken(HttpServletRequest request){
        try{
            Optional<Cookie> cookies = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(AUTHORIZATION_HEADER))
                    .filter(cookie-> cookie.getValue().startsWith(BEARER_PREFIX))
                    .findFirst();

            if(cookies.isPresent()) {
                String bearerToken = cookies.get().getValue();
                if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX))
                    return bearerToken.substring(7);
            }
        }catch (Exception e){

            return null;
        }

        return null;
    }

    public Key getKey() {
        return key;
    }

    public String generateToken(String id){
        Claims claims = Jwts.claims();
        claims.put("type", "user");
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(id)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(Instant.now().plus(EXPIRATION_TIME, ChronoUnit.MILLIS)))
                .signWith(key).compact();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (SignatureException e){
            log.info("Invalid Jwt signature");
            log.trace("Invalid Jwt signature: trace{}", e);
        }catch (MalformedJwtException e){
            log.info("Invalid Jwt token");
            log.trace("Invalid Jwt token trace: {}", e);
        }catch (ExpiredJwtException e){
            log.info("Expired Jwt token");
            log.trace("Expired Jwt token trace: {}", e);
        }catch (UnsupportedJwtException e){
            log.info("Unsupported Jwt token");
            log.trace("Unsupported Jwt token trace: {}", e);
        }catch (IllegalArgumentException e){
            log.info("Jwt token compact of handler are invalid");
            log.trace("Jwt token compact of handler are invalid trace: {}", e);
        }
        return false;
    }

    public String extractId(String token){
        return extractClaim(token, Claims::getSubject);}
    public Boolean validateToken(String token, UserDetails userDetails){
        final var id = extractId(token);
        return id.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date extractExpiration(String token){ return extractClaim(token, Claims::getExpiration);}
    public Boolean isTokenExpired(String token){ return extractExpiration(token).before(new Date());}

}
