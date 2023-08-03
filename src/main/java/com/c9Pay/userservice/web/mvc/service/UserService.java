package com.c9Pay.userservice.web.mvc.service;

import com.c9Pay.userservice.data.entity.User;
import com.c9Pay.userservice.security.jwt.JwtTokenUtil;
import com.c9Pay.userservice.web.exception.DuplicatedUserException;
import com.c9Pay.userservice.web.exception.UserNotFoundException;
import com.c9Pay.userservice.data.dto.user.UserUpdateParam;
import com.c9Pay.userservice.web.mvc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public void signUp(User user){
        if(!validateDuplicateUserId(user.getUserId()))
            throw new DuplicatedUserException(String.format("ID[%s] is Duplicated", user.getUserId()));
        String encodedPassword= passwordEncoder.encode(user.getPassword());
        user.encodePassword(encodedPassword);
        userRepository.save(user);
    }

    public String authenticate(String userId, String password){
        User findUser = userRepository.findByUserId(userId).orElse(null);
        log.info("find user id : {}", Objects.requireNonNull(findUser).getId());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        findUser.getId().toString(),
                        password
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenUtil.generateToken(findUser.getId().toString());
    }

    public User findUserByUserId(String userId){
        return userRepository.findByUserId(userId).orElseThrow(()-> new UserNotFoundException(String.format("User ID[%s] not found",userId )));
    }

    public boolean validateDuplicateUserId(String userId){
        return userRepository.findByUserId(userId).isEmpty();
    }

    @Transactional
    public void deleteUserById(Long id){
        userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException(String.format("ID[%s] doesn't exist, can not delete", id)));
        userRepository.deleteById(id);
    }

    @Transactional
    public void updateUserById(Long id, UserUpdateParam param){
        User findUser = findById(id);
        String encode = passwordEncoder.encode(findUser.getPassword());
        param.setPassword(encode);
        findUser.updateUser(param);
    }
    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(String.format("ID[%s] not found", id)));
    }


}
