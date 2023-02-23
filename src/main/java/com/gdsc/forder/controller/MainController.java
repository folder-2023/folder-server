package com.gdsc.forder.controller;

import com.gdsc.forder.dto.GetFillDTO;
import com.gdsc.forder.dto.UserDTO;
import com.gdsc.forder.service.CustomUserDetailService;
import com.gdsc.forder.service.OldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;

@Api(tags = "메인 화면 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class MainController {

    private final OldService oldService;
    private final CustomUserDetailService customUserDetailService;

    @ApiOperation(value = "약 정보 조회 엔드 포인트")
    @GetMapping("old/fillInfo")
    public List<GetFillDTO> getFillInfo(@ApiIgnore Principal principal){
        UserDTO user = customUserDetailService.findUser(principal);
        return oldService.getFillInfo(user.getId());
    }


}
