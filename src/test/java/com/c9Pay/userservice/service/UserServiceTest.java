package com.c9Pay.userservice.service;

import com.c9Pay.userservice.entity.User;
import com.c9Pay.userservice.web.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    @Transactional
    void signUp_DuplicateUser_ExceptionThrown() {
        //given
        User user = User.builder()
                .userId("aaa")
                .credit(0L)
                .username("testName")
                .password("pass")
                .email("aa@gmail.com")
                .build();
        //when
        userService.signUp(user);

        //then
        assertThrows(IllegalStateException.class, () -> userService.signUp(user));
    }
}