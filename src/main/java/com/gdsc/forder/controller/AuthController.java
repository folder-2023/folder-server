package com.gdsc.forder.controller;

import com.gdsc.forder.dto.*;
import com.gdsc.forder.service.CustomUserDetailService;
import com.gdsc.forder.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

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


    //회원가입
    @PostMapping("/join")
    public UserDTO join(@Valid @RequestBody JoinUserDTO joinUserDTO, @ModelAttribute AddFillDTO addFillDTO) throws Exception {

        UserDTO user = userService.singUp(joinUserDTO);
        Long userId = user.getId();

        if(addFillDTO.getFills().size() > 0){
            userService.addFill(addFillDTO, userId);
        }
        return user;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginUserDTO loginUserDTO) {
        return userService.login(loginUserDTO);
    }
}
