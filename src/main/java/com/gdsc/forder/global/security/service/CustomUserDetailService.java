package com.gdsc.forder.global.security.service;

import java.util.Optional;

import com.gdsc.forder.global.security.domain.Role;
import com.gdsc.forder.global.security.domain.User;
import com.gdsc.forder.global.security.dto.JoinUserDTO;
import com.gdsc.forder.global.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public CustomUserDetailService(UserRepository userRepository, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User joinUser(JoinUserDTO joinUserDTO){
        String email = joinUserDTO.getEmail();
        String password = joinUserDTO.getPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        User user = new User(email, encodedPassword, Role.USER);
        userRepository.save(user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }
}
