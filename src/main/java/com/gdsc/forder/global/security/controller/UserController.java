package com.gdsc.forder.global.security.controller;

import com.gdsc.forder.global.security.domain.User;
import com.gdsc.forder.global.security.dto.JoinUserDTO;
import com.gdsc.forder.global.security.provider.JwtTokenProvider;
import com.gdsc.forder.global.security.repository.UserRepository;
import com.gdsc.forder.global.security.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final CustomUserDetailService userDetailService;

    @GetMapping("/user/me")
    public User getCurrentUser(@ApiIgnore Principal principal) {
        try{
            User user = (User) userDetailService.loadUserByUsername(principal.getName());
            return user;
        }catch (NullPointerException e){
            throw e;
        }
    }

    // 회원가입
    @PostMapping("/join")
    public User join(@RequestBody JoinUserDTO joinUserDTO) {
        return userDetailService.joinUser(joinUserDTO);
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody JoinUserDTO joinUserDTO) {
        User user = userRepository.findByEmail(joinUserDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(joinUserDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(user.getUsername());
    }

//    // 회원가입
//    @PostMapping("/join")
//    public Long join(@RequestBody Map<String, String> user) {
//        return userRepository.save(User.builder()
//                .email(user.get("email"))
//                .password(passwordEncoder.encode(user.get("password")))
//                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
//                .build()).getId();
//    }

//    // 로그인
//    @PostMapping("/login")
//    public String login(@RequestBody Map<String, String> user) {
//        User member = userRepository.findByEmail(user.get("email"))
//                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
//        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
//            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
//        }
//        return jwtTokenProvider.createToken(member.getUsername());
//    }

}
