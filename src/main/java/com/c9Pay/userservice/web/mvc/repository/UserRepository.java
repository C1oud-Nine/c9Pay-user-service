package com.c9Pay.userservice.web.mvc.repository;


import com.c9Pay.userservice.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String UserId);
    Optional<User> findById(Long id);

}
