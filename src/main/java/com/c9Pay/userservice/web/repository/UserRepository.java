package com.c9Pay.userservice.web.repository;


import com.c9Pay.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String UserId);
    Optional<User> findById(Long id);

}

