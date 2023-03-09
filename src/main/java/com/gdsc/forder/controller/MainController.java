package com.gdsc.forder.controller;

import com.gdsc.forder.domain.User;
import com.gdsc.forder.dto.AddCalendarDTO;
import com.gdsc.forder.dto.GetCalendarDTO;
import com.gdsc.forder.dto.GetFillDTO;
import com.gdsc.forder.dto.UserDTO;
import com.gdsc.forder.service.CustomUserDetailService;
import com.gdsc.forder.service.OldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;

@Api(tags = "메인 화면 API")
@RestController
@RequiredArgsConstructor
@EnableScheduling
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
        oldService.checkFill(user.getId(), fillId, accept);
        return oldService.getFillOne(user.getId(), fillId);
    }


    //매일 새벽 1시 복용 여부 false 로 초기화 해주기
    //    @Scheduled(cron = "0 0 1 * * *")
    @ApiOperation(value = "모든 회원들 약 복용 여부 자동 초기화")
    @PatchMapping("old/fillInfo/schedule")
    public void resetFillCheck(){
        oldService.resetFillCheck();
    }

    @ApiOperation(value = "일정 등록 엔드 포인트")
    @PostMapping("calendar")
    public GetCalendarDTO saveCalendar(@ApiIgnore Principal principal, @RequestBody AddCalendarDTO addCalendarDTO){
        Long userId = customUserDetailService.findUser(principal).getId();
        return oldService.saveCalendar(userId, addCalendarDTO);
    }

    @ApiOperation(value = "일정 조회 엔드 포인트")
    @GetMapping("calendar")
    public List<GetCalendarDTO> getCalendar(@ApiIgnore Principal principal){
        Long userId = customUserDetailService.findUser(principal).getId();
        return oldService.getCalendar(userId);
    }

    @ApiOperation(value = "일정 수정 엔드 포인트")
    @PatchMapping("calendar/{calendarId}")
    public GetCalendarDTO modCalendar(@ApiIgnore Principal principal, @PathVariable("calendarId") long calendarId, @RequestBody AddCalendarDTO addCalendarDTO){
        Long userId = customUserDetailService.findUser(principal).getId();
        return oldService.modeCalendar(userId, calendarId, addCalendarDTO);
    }

//    @ApiOperation(value = "일정 삭제 엔드 포인트")
//    @DeleteMapping("calendar/{calendarId}")
//    public String delCalendar(@ApiIgnore Principal principal, @PathVariable("calendarId") long calendarId){
//        Long userId = customUserDetailService.findUser(principal).getId();
//
//    }
}
