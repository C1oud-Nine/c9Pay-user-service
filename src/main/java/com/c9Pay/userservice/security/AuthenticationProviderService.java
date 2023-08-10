package com.c9Pay.userservice.security;

import com.c9Pay.userservice.data.entity.User;
import com.c9Pay.userservice.web.exception.exceptions.UserNotFoundException;
import com.c9Pay.userservice.web.mvc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static com.c9Pay.userservice.constant.BearerConstant.BEARER_PREFIX;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationProviderService implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String id =authentication.getName();
        String password= authentication.getCredentials().toString();
        User user = userRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new UserNotFoundException("user not found!"));
        log.debug("raw encoded {}", passwordEncoder.encode(password));
        log.debug("raw password: {}, encoded password {}", password, user.getPassword());
        log.debug("is matched? : {}", passwordEncoder.matches(password,user.getPassword()));
        return checkPassword(user, password, passwordEncoder);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private Authentication checkPassword(User user, String rawPassword, PasswordEncoder encoder){
        if(encoder.matches(rawPassword,user.getPassword())){
            return new UsernamePasswordAuthenticationToken(user.getId(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority(BEARER_PREFIX)));
        }else throw new BadCredentialsException("Bad Credential!!!");
    }
}
