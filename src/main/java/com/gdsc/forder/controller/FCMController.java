package com.gdsc.forder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.forder.domain.Alarm;
import com.gdsc.forder.dto.PushNotificationDTO;
import com.gdsc.forder.dto.PushNotificationResponse;
import com.gdsc.forder.dto.UserDTO;
import com.gdsc.forder.repository.AlarmRepository;
import com.gdsc.forder.service.CustomUserDetailService;
import com.gdsc.forder.service.FCMService;
import com.gdsc.forder.service.PushNotificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Principal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Api(tags = "푸시 알림 API")
@RequestMapping("/fcm")
@RestController
@RequiredArgsConstructor
public class FCMController {

    private final PushNotificationService pushNotificationService;
    private final CustomUserDetailService customUserDetailService;
    private final AlarmRepository alarmRepository;

    @ApiOperation(value = "주제(topic) 별 알림 전송 엔드 포인트", notes = "token 값 null 로 보내도 된다.")
    @PostMapping("/notification/topic")
    public PushNotificationDTO sendNotification(@RequestBody PushNotificationDTO request) {
        pushNotificationService.sendPushNotificationWithoutData(request);
        return request;
    }


    @ApiOperation(value = "특정 기기 별 알림 전송 엔드 포인트", notes = "topic 값 null 로 보내도 된다.")
    @PostMapping("/notification/token")
    public PushNotificationDTO sendTokenNotification(@ApiIgnore Principal principal, @RequestBody PushNotificationDTO request) {

        UserDTO user = customUserDetailService.findUser(principal);
        Optional<Alarm> alarm = alarmRepository.findByUser(user.getUsername()+user.getId());

        LocalTime time = LocalTime.now();
        String now = time.format(DateTimeFormatter.ofPattern("HH:mm:00"));

        if(alarm.isPresent() && alarm.get().getAlarmTime().equals(now)){

            request.setToken(user.getFcmToken());
            request.setMessage(alarm.get().getMessage());
            request.setTitle(alarm.get().getTitle());
            request.setTopic(alarm.get().getTopic());

            pushNotificationService.sendPushNotificationToToken(request);
            return request;
        }
        else
            return null;
    }

    /**
     * 알림 언제?
     * 아침점심저녁 밥 먹으라고
     * 하루 마무리에 약 복용 확인
     */

    @ApiOperation(value = "주제(topic) 구독 엔드 포인트", notes = "구독할 topic의 내용과, 기기 token값을 파라미터로 입력해서 topic을 구독한다.")
    @GetMapping("subscribe/topic")
    public String testServiceApi(@RequestParam("token")String token, @RequestParam("topic")String topic) throws Exception {
        pushNotificationService.subscribeTokenToTopic(token, topic);
        return "구독 성공";
    }
}