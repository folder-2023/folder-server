package com.gdsc.forder.service;

import com.gdsc.forder.dto.*;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    //회원가입
    public UserDTO singUp(JoinUserDTO joinUserDTO) throws Exception;

    //로그인
    public String login(LoginUserDTO loginUserDTO);

}
