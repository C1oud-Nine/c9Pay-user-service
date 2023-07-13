package com.c9Pay.userservice.entity;

import com.c9Pay.userservice.web.dto.user.UserUpdateParam;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name= "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private UUID serialNumber;


    private Long credit;

    public void updateUser(UserUpdateParam param) {
        this.email = param.getEmail();
        this.password = param.getPassword();
        this.userId = param.getUserId();
        this.username = param.getUsername();
    }
}
