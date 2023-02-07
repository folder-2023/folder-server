package com.gdsc.forder.global.security.service;

import com.gdsc.forder.global.security.domain.User;
import com.gdsc.forder.global.security.domain.UserDetailImpl;
import com.gdsc.forder.global.security.dto.LoginUserDTO;
import com.gdsc.forder.global.security.dto.UserDTO;
import com.gdsc.forder.global.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public User findUser(Principal principal){
        Optional<User> user = userRepository.findByLoginId(principal.getName());
        return user.get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLoginId(username)
                .map(UserDetailImpl::new)
                .orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을 수 없습니다"));
    }
}
