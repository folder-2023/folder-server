package com.gdsc.forder.global.security.service;

import com.gdsc.forder.global.security.dto.JoinUserDTO;
import com.gdsc.forder.global.security.dto.LoginUserDTO;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService {

    //회원가입
    public Long singUp(JoinUserDTO joinUserDTO) throws Exception;

    //로그인
    public String login(LoginUserDTO loginUserDTO);
}
