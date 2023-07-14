package com.c9Pay.userservice.web.service;

import com.c9Pay.userservice.entity.User;
import com.c9Pay.userservice.web.dto.user.UserDto;
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
        if(validateDuplicateUserId(user.getUserId())) throw new IllegalStateException("이미 존재하는 회원입니다.");
        userRepository.save(user);
    }

    public Long authenticate(String userId, String password){
        User findUser = findUserByUserId(userId).orElse(null);
        //추후 BCrypt 추가시 수정 필요
        if(findUser == null || !findUser.getPassword().equals(password))
            throw new IllegalStateException();

        return findUser.getId();
    }

    public Optional<User> findUserByUserId(String userId){
        return userRepository.findByUserId(userId);
    }

    public boolean validateDuplicateUserId(String userId){
        return userRepository.findByUserId(userId).isPresent();
    }

    @Transactional
    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

    @Transactional
    public void updateUserById(Long id, UserUpdateParam param){
        User findUser = userRepository.findById(id).orElseThrow(NullPointerException::new);
        findUser.updateUser(param);
    }
    public User findById(Long id){
        return userRepository.findById(id).orElse(null);
    }


}
