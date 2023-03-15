package com.gdsc.forder.controller;

import com.gdsc.forder.dto.PushNotificationDTO;
import com.gdsc.forder.dto.PushNotificationResponse;
import com.gdsc.forder.service.FCMService;
import com.gdsc.forder.service.PushNotificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "푸시 알림 API")
@RequestMapping("/fcm")
@RestController
@RequiredArgsConstructor
public class FCMController {

    private final PushNotificationService pushNotificationService;
    private final FCMService fcmService;


    @PostMapping("/notification/topic")
    public PushNotificationDTO sendNotification(@RequestBody PushNotificationDTO request) {
        pushNotificationService.sendPushNotificationWithoutData(request);
        return request;
    }

    @PostMapping("/notification/token")
    public PushNotificationDTO sendTokenNotification(@RequestBody PushNotificationDTO request) {
        pushNotificationService.sendPushNotificationToToken(request);
        return request;
    }

    /**
     * 알림 언제?
     * 아침점심저녁 밥 먹으라고
     * 하루 마무리에 약 복용 확인
     */

}