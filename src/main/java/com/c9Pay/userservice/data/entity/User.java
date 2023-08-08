package com.c9Pay.userservice.data.entity;

import com.c9Pay.userservice.data.dto.user.UserUpdateParam;
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
public class User extends BaseTimeEntity {
    @Override
    public boolean equals(UserUpdateParam user) {
        return (this.email.equals(user.getEmail())
                && this.username.equals(user.getUsername())
                && this.userId.equals(user.getUserId()));
    }

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


    public void updateUser(UserUpdateParam param) {
        this.email = param.getEmail();
        this.password = param.getPassword();
        this.userId = param.getUserId();
        this.username = param.getUsername();
    }
    public void encodePassword(String password){
        this.password = password;
    }
}
