package com.gdsc.forder.global.security.controller;

import com.gdsc.forder.global.security.domain.User;
import com.gdsc.forder.global.security.dto.JoinUserDTO;
import com.gdsc.forder.global.security.dto.LoginUserDTO;
import com.gdsc.forder.global.security.dto.UserDTO;
import com.gdsc.forder.global.security.provider.JwtTokenProvider;
import com.gdsc.forder.global.security.repository.UserRepository;
import com.gdsc.forder.global.security.service.CustomUserDetailService;
import com.gdsc.forder.global.security.service.UserService;
import com.gdsc.forder.global.security.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserServiceImpl userService;
    private final CustomUserDetailService customUserDetailService;

    @GetMapping("/me")
    public UserDTO getCurrentUser(@ApiIgnore Principal principal) {
        try{
            return customUserDetailService.findUser(principal);
        }catch (NullPointerException e){
            throw e;
        }
    }

    // 회원가입
    @PostMapping("/join")
    public Long join(@Valid @RequestBody JoinUserDTO joinUserDTO) throws Exception {
        return userService.singUp(joinUserDTO);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginUserDTO loginUserDTO) {
        return userService.login(loginUserDTO);
    }
}
