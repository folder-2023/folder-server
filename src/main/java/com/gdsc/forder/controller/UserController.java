package com.gdsc.forder.controller;

import com.gdsc.forder.dto.AddFamilyDTO;
import com.gdsc.forder.dto.LoginUserDTO;
import com.gdsc.forder.dto.UserDTO;
import com.gdsc.forder.service.CustomUserDetailService;
import com.gdsc.forder.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;

@Api(tags = "마이 페이지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("my-page")
public class UserController {

    private final UserService userService;
    private final CustomUserDetailService customUserDetailService;

    @PostMapping("/family")
    @ApiOperation(value = "보호자/대상 추가 요청 엔드포인트")
    public AddFamilyDTO.reqFamily  reqFamily(@ApiIgnore Principal principal, @RequestParam("userCode")Long userCode) {
        UserDTO user = customUserDetailService.findUser(principal);
        String userName = user.getUsername();
        String familyName = userService.findByUserCode(userCode);

        return new AddFamilyDTO.reqFamily(userName,familyName, userCode);
    }

    @PatchMapping("/family")
    @ApiOperation(value = "보호자/대상 추가 수락여부 엔드포인트")
    public AddFamilyDTO.acceptFamily addFamily(@ApiIgnore Principal principal, @RequestParam("accept")Boolean accept, @RequestParam("userCode")Long userCode ) {
        UserDTO user = customUserDetailService.findUser(principal);
        if(accept){
            UserDTO family = userService.addFamily(user.getId(), userCode);
            user.setFamilyId(family.getId());
            return new AddFamilyDTO.acceptFamily(user, family);
        }
        return new AddFamilyDTO.acceptFamily(user, null);
    }
}
