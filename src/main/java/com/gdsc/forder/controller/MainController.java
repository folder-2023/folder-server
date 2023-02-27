package com.gdsc.forder.controller;

import com.gdsc.forder.dto.GetFillDTO;
import com.gdsc.forder.dto.UserDTO;
import com.gdsc.forder.service.CustomUserDetailService;
import com.gdsc.forder.service.OldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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

    @ApiOperation(value = "약 복용 여부 체크 엔드 포인트")
    @PatchMapping("old/fillInfo/{fillId}")
    public GetFillDTO checkFill(@ApiIgnore Principal principal, @PathVariable("fillId") long fillId, @RequestParam("accept")Boolean accept){
        UserDTO user = customUserDetailService.findUser(principal);
        oldService.checkfill(user.getId(), fillId, accept);
        return oldService.getFillOne(user.getId(), fillId);
    }
}
