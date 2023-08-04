package com.c9Pay.userservice.security.customuserdetails;


import com.c9Pay.userservice.data.entity.User;
import com.c9Pay.userservice.security.customuserdetails.CustomUserDetails;
import com.c9Pay.userservice.web.mvc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<User> findUser = userRepository.findById(Long.valueOf(userId));
        if(findUser.isPresent()){
            User user = findUser.get();
            List<GrantedAuthority> authorityList = List.of(new SimpleGrantedAuthority("USER"));
            return new CustomUserDetails(user.getId().toString(), user.getPassword(), authorityList);
        }
        return null;
    }

}

