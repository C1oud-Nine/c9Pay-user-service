package com.c9Pay.userservice.web.service;

import com.c9Pay.userservice.entity.User;
import com.c9Pay.userservice.web.exception.DuplicatedUserException;
import com.c9Pay.userservice.web.exception.LoginFailedException;
import com.c9Pay.userservice.web.exception.UserNotFoundException;
import com.c9Pay.userservice.web.dto.user.UserUpdateParam;
import com.c9Pay.userservice.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //@TODO:EXCEPTION 처러해둔 것 수정
    @Transactional
    public void signUp(User user){
        //@TODO: feign Client(auth-service: 개체 식별번호 생성 요청, credit-service: 계좌 생성 요청)
        if(!validateDuplicateUserId(user.getUserId()))
            throw new DuplicatedUserException(String.format("ID[%s] is Duplicated", user.getUserId()));
        userRepository.save(user);
    }

    public Long authenticate(String userId, String password){
        User findUser = userRepository.findByUserId(userId).orElse(null);
        //추후 BCrypt 추가시 수정 필요
        if(findUser == null || !findUser.getPassword().equals(password))
            throw new LoginFailedException();

        return findUser.getId();
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
        findUser.updateUser(param);
    }
    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(String.format("ID[%s] not found", id)));
    }


}
