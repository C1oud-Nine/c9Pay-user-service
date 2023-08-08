package com.c9Pay.userservice.web.mvc.controller;

import com.c9Pay.userservice.constant.CookieConstant;
import com.c9Pay.userservice.data.dto.user.LoginForm;
import com.c9Pay.userservice.data.dto.user.UserDto;
import com.c9Pay.userservice.data.entity.User;
import com.c9Pay.userservice.security.jwt.JwtTokenUtil;
import com.c9Pay.userservice.web.exception.IllegalTokenDetailException;
import com.c9Pay.userservice.web.exception.UserNotFoundException;
import com.c9Pay.userservice.web.mvc.service.UserService;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;

import static com.c9Pay.userservice.constant.CookieConstant.AUTHORIZATION_HEADER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class LoginControllerTest {

    @Autowired private UserService userService;
    @Autowired private LoginController loginController;
    @Autowired private UserController userController;
    @Autowired private JwtTokenUtil jwtTokenUtil;

    @Test
    @DisplayName("login / success")
    public void testLoginSuccess() throws Exception{
        //given
        UserDto dto = new UserDto("alphabet", "aa", "bb", "aa@bb.cc");
        userController.signUp(dto);
        LoginForm form = new LoginForm("aa", "bb");
        MockHttpServletResponse response = new MockHttpServletResponse();
        User findUser = userService.findUserByUserId("aa");
        //when
        loginController.login(form, response);
        Cookie cookie = response.getCookie(AUTHORIZATION_HEADER);
        if(cookie == null || cookie.getValue() == null) fail();
        String token = cookie.getValue();
        String id = parseToken(token);
        //then
        assertTrue(jwtTokenUtil.validateToken(token.substring(7)));
        assertThat(Long.parseLong(id)).isEqualTo(findUser.getId());
     }

     @Test
     @DisplayName("login / fail(id)")
     public void testLoginFailById(){
         //given
         UserDto dto = new UserDto("alphabet", "dd1", "cc", "aa@bb.cc");
         userController.signUp(dto);
         LoginForm form = new LoginForm("aa", "cc");
         MockHttpServletResponse response = new MockHttpServletResponse();

         //then
         assertThrows(BadCredentialsException.class,() ->loginController.login(form, response));
         Cookie cookie = response.getCookie(AUTHORIZATION_HEADER);
         if(cookie != null ) fail();
     }

    @Test
    @DisplayName("login / fail(password)")
    public void testLoginFailByPassword(){
        //given
        UserDto dto = new UserDto("alphabet", "dd2", "cc", "aa@bb.cc");
        userController.signUp(dto);
        LoginForm form = new LoginForm("dd2", "bb");
        MockHttpServletResponse response = new MockHttpServletResponse();

        //then
        assertThrows(BadCredentialsException.class,() ->loginController.login(form, response));
        Cookie cookie = response.getCookie(AUTHORIZATION_HEADER);
        if(cookie != null ) fail();
    }

    @Test
    @DisplayName("login / fail(both)")
    public void testLoginFail(){
        //given
        UserDto dto = new UserDto("alphabet", "dd3", "cc", "aa@bb.cc");
        userController.signUp(dto);
        LoginForm form = new LoginForm("aa", "ff");
        MockHttpServletResponse response = new MockHttpServletResponse();

        //then
        assertThrows(BadCredentialsException.class,() ->loginController.login(form, response));
        Cookie cookie = response.getCookie(AUTHORIZATION_HEADER);
        if(cookie != null ) fail();
    }


    private String parseToken(String token) {
        if(token.length() >= 7){
            String parsedToken = token.substring(7);
            return jwtTokenUtil.extractId(parsedToken);
        }
        throw new IllegalTokenDetailException();
    }


}