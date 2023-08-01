package com.c9Pay.userservice.jwt;

import com.c9Pay.userservice.constant.CookieConstant;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;
import java.util.List;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static com.c9Pay.userservice.constant.CookieConstant.AUTHORIZATION_HEADER;
import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter implements Filter {

    private final TokenProvider tokenProvider;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String jwt = resolveToken(httpServletRequest);

        if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt, httpServletRequest)){
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            if(!(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")))){
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = null;

        if (request.getCookies() != null) {
            bearerToken = Arrays.stream(request.getCookies()).filter((cookie -> cookie.getName().equals(AUTHORIZATION_HEADER)))
                    .findFirst().map(cookie -> cookie.getValue()).orElse(null);
        }
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer+")){
            bearerToken = URLDecoder.decode(bearerToken, UTF_8);
            log.debug("해더의 토큰 {}", bearerToken);
            String token = bearerToken.substring(7);
            log.debug("jwt 토큰 {}", token);
            return token;
        }
        return null;
    }
}
