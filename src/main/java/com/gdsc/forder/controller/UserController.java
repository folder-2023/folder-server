package com.gdsc.forder.controller;

import com.gdsc.forder.dto.*;
import com.gdsc.forder.service.CustomUserDetailService;
import com.gdsc.forder.service.OldService;
import com.gdsc.forder.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;

@Api(tags = "마이 페이지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("my-page")
public class UserController {

    private final UserService userService;
    private final OldService oldService;
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

    @GetMapping("/fillInfo")
    @ApiOperation(value = "약 복용 일지 조회 엔드 포인트 (마이 페이지)")
    public List<GetFillDTO> getFillInfo(@ApiIgnore Principal principal) {
        UserDTO user = customUserDetailService.findUser(principal);
        return oldService.getFillInfo(user.getId());
    }

//    @PatchMapping("/fillInfo/{fillId}")
//    @ApiOperation(value = "약 복용 일지 수정 엔드 포인트")
//    public List<GetFillDTO> editFillInfo(@ApiIgnore Principal principal, @PathVariable("fillId") long fillId, @RequestBody EditFillDTO editFillDTO) {
//        UserDTO user = customUserDetailService.findUser(principal);
//        userService.editFillInfo(user.getId(), fillId, editFillDTO);
//        return oldService.getFillInfo(user.getId());
//    }

    @PatchMapping("/fillInfo/{fillId}")
    @ApiOperation(value = "약 복용 일지 삭제 엔드 포인트")
    public List<GetFillDTO> deleteFillInfo(@ApiIgnore Principal principal, @PathVariable("fillId") long fillId) {
        UserDTO user = customUserDetailService.findUser(principal);
        userService.deleteFillInfo(user.getId(), fillId);
        return oldService.getFillInfo(user.getId());
    }

    @PostMapping("/fillInfo")
    @ApiOperation(value = "약 복용 일지 추가 엔드 포인트")
    public List<GetFillDTO> addFillInfo(@ApiIgnore Principal principal, @ModelAttribute AddFillDTO addFillDTO) {
        UserDTO user = customUserDetailService.findUser(principal);
        if(addFillDTO.getFills().size() > 0){
            userService.addFill(addFillDTO, user.getId());
        }
        return oldService.getFillInfo(user.getId());
    }
}
