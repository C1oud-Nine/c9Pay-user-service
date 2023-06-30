package com.c9Pay.userservice.web.service;

import com.c9Pay.userservice.entity.User;
import com.c9Pay.userservice.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void signUp(User user){
        //@TODO: feign Client(auth-service: 개체 식별번호 생성 요청, credit-service: 계좌 생성 요청)
        if(validateDuplicateUserId(user.getUserId())) throw new IllegalStateException("이미 존재하는 회원입니다.");
        userRepository.save(user);
    }

    public boolean validateDuplicateUserId(String userId){
        return userRepository.findByUserId(userId).isPresent();
    }

}
