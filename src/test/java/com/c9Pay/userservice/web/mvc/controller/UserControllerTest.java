package com.c9Pay.userservice.web.mvc.controller;

import com.c9Pay.userservice.data.dto.user.UserDto;
import com.c9Pay.userservice.data.entity.User;
import com.c9Pay.userservice.security.jwt.JwtAuthenticationFilter;
import com.c9Pay.userservice.security.jwt.JwtTokenUtil;
import com.c9Pay.userservice.web.client.AuthClient;
import com.c9Pay.userservice.web.client.CreditClient;
import com.c9Pay.userservice.web.exception.DuplicatedUserException;
import com.c9Pay.userservice.web.exception.IllegalTokenDetailException;
import com.c9Pay.userservice.web.mvc.service.UserService;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class UserControllerTest {

    @Autowired private AuthClient authClient;

    @Autowired private CreditClient creditClient;

    @Autowired private JwtTokenUtil jwtTokenUtil;

    @Autowired private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired private UserService userService;

    @Autowired private UserController userController;

    @Autowired private PasswordEncoder passwordEncoder;
    @Test
    public void testSingUp(){
        //given
        UserDto form = new UserDto("test-dummy", "test-id",
                "test-password","test@email.com");

        //when
        ResponseEntity<?> signUpResponse = userController.signUp(form);
        User findUser = userService
                .findBySerialNumber(requireNonNull(signUpResponse.getBody())
                .toString());

        //then
        assertThat(signUpResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(findUser.getUserId()).isEqualTo("test-id");
        assertThat(findUser.getUsername()).isEqualTo("test-dummy");
        assertTrue(passwordEncoder.matches("test-password", findUser.getPassword()));
        assertThat(findUser.getEmail()).isEqualTo("test@email.com");
        validateSerialNumber(findUser.getSerialNumber().toString());
        deleteAccount(findUser.getSerialNumber().toString());
        userService.deleteUserById(findUser.getId());
    }

    @Test
    public void testDuplicatedSignUp(){
        //given
        UserDto one = new UserDto("testA", "test1", "test1", "test@test.com");
        UserDto two = new UserDto("testB", "test1", "test2","test@test.ac.kr");

        //when
        ResponseEntity<?> first = userController.signUp(one);
        User testA = userService.findUserByUserId("test1");
        //then
        deleteAccount(testA.getSerialNumber().toString());
        assertThat(first.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertFalse(passwordEncoder.matches("test2", testA.getPassword()));
        assertThrows(DuplicatedUserException.class, ()-> userController.signUp(two));
        userService.deleteOneByName(testA.getUsername());
    }

    @Test
    public void testGetUserDetails(){
        //given
        UserDto dto = new UserDto("testA","test", "testB", "test@test.test");
        User user = dto.toEntity(UUID.randomUUID());
        userService.signUp(user);
        User findUser = userService.findUserByUserId("test");
        //when
        String token = jwtTokenUtil.generateToken(String.valueOf(findUser.getId()));
        ResponseEntity<?> response = userController.getUserDetail(token);
        UserDto body = (UserDto) response.getBody();
        //then
        assertTrue(jwtTokenUtil.validateToken(token));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(requireNonNull(body).getUsername()).isEqualTo("testA");
        assertThat(requireNonNull(body).getUserId()).isEqualTo("test");
        assertThat(requireNonNull(body).getPassword()).isEqualTo("testB");
        assertThat(requireNonNull(body).getEmail()).isEqualTo("test@test.test");
    }

    @Test
    public void testUpDateUser(){
        //given
        //when

        //then
    }
    private void deleteAccount(String serialNumber){
        ResponseEntity<?> deleteResponse = creditClient.deleteAccount(serialNumber);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private void validateSerialNumber(String id){
        ResponseEntity<?> responseEntity = authClient.validateSerialNumber(id);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private String parseToken(String token) {
        if(token.length() >= 7){
            String parsedToken = token.substring(7);
            return jwtTokenUtil.extractId(parsedToken);
        }
        throw new IllegalTokenDetailException();
    }
}