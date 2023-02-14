package com.gdsc.forder.controller;

import com.gdsc.forder.dto.*;
import com.gdsc.forder.service.CustomUserDetailService;
import com.gdsc.forder.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;

@Api(tags = "로그인 / 회원가입 API")
@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final UserService userService;
    private final CustomUserDetailService customUserDetailService;

    @GetMapping("/me")
    @ApiOperation(value = "개발용 토큰 확인 엔드포인트", notes = "header에 토큰을 넣었을 때 회원정보가 제대로 조회되는지 확인하는 엔드포인트")
    public UserDTO getCurrentUser(@ApiIgnore Principal principal) {
        try{
            return customUserDetailService.findUser(principal);
        }catch (NullPointerException e){
            throw e;
        }
    }


    //회원가입
    @PostMapping("/join")
    @ApiResponse(
            code = 200
            , message = "accessToken, userDTO 반환"
    )
    @ApiOperation(value = "회원가입 엔드포인트" , notes =
            "JoinUserDTO에 맞춰서 requestBody로 회원정보를 입력한다." +
                    "약 정보는 string list로 입력하고 parameter로 보낸다 " +
            "fillTimes는 HH:mm 형식으로 작성한다. ")
    public SignInDTO join(@Valid @RequestBody JoinUserDTO joinUserDTO, @ModelAttribute AddFillDTO addFillDTO) throws Exception {

        UserDTO user = userService.singUp(joinUserDTO);
        Long userId = user.getId();

        if(addFillDTO.getFills().size() > 0){
            userService.addFill(addFillDTO, userId);
        }

        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO.setLoginId(joinUserDTO.getLoginId());
        loginUserDTO.setPassword(joinUserDTO.getPassword());
        String accessToken = userService.login(loginUserDTO);

        return new SignInDTO(accessToken, user);
    }

    @PostMapping("/login")
    @ApiResponse(
            code = 200
            , message = "accessToken 발급"
    )
    @ApiOperation(value = "로그인 엔드포인트", notes = "LoginUserDTO에 맞춰서 아이디와 비밀번호를 입력한다.")
    public String login(@RequestBody LoginUserDTO loginUserDTO) {
        return userService.login(loginUserDTO);
    }
}
