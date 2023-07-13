package com.c9Pay.userservice.web.controller;

import com.c9Pay.userservice.jwt.TokenProvider;
import com.c9Pay.userservice.web.dto.user.LoginForm;
import com.c9Pay.userservice.web.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Transactional
public class LoginControllerTest {

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private UserService userService;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        loginController = new LoginController(tokenProvider, userService);
    }

    @Test
    public void testLoginWithValidCredentials() throws IOException {
        LoginForm form = new LoginForm();
        form.setUserId("user123");
        form.setPassword("password123");

        Long userId = 123L;
        String token = "dummy-token";

        when(userService.authenticate(form.getUserId(), form.getPassword())).thenReturn(userId);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userId, "");
        //when(tokenProvider.createToken(authentication, request.getRemoteAddr())).thenReturn(token);

        ResponseEntity<String> responseEntity = loginController.login(form, request,response);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("로그인 성공", responseEntity.getBody());

        verify(response).addCookie(any(Cookie.class));
    }

    @Test
    public void testLoginWithInvalidCredentials() throws IOException {
        LoginForm form = new LoginForm();
        form.setUserId("user456");
        form.setPassword("wrongPassword");

        when(userService.authenticate(form.getUserId(), form.getPassword())).thenThrow(new RuntimeException());

        ResponseEntity<String> responseEntity = loginController.login(form, request,response);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        verify(response, never()).addCookie(any(Cookie.class));
    }

}
