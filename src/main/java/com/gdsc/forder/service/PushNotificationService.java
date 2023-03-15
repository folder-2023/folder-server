package com.gdsc.forder.service;

import java.util.HashMap;
import java.util.Map;

import com.gdsc.forder.dto.PushNotificationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationService {

    private Logger logger = LoggerFactory.getLogger(PushNotificationService.class);
    private FCMService fcmService;

    public PushNotificationService(FCMService fcmService) {
        this.fcmService = fcmService;
    }

    public void sendPushNotification(PushNotificationDTO request) {
        try {
            fcmService.sendMessage(getSamplePayloadData(), request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void sendPushNotificationWithoutData(PushNotificationDTO request) {
        try {
            fcmService.sendMessageWithoutData(request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


    public void sendPushNotificationToToken(PushNotificationDTO request) {
        try {
            fcmService.sendMessageToToken(request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


    private Map<String, String> getSamplePayloadData() {
        Map<String, String> pushData = new HashMap<>();
        pushData.put("messageId", "msgid");
        pushData.put("text", "txt");
        pushData.put("user", "pankaj singh");
        return pushData;
    }

    private void subscribeTokenToTopic(String token, String topic){

    }


//    private PushNotificationRequest getSamplePushNotificationRequest() {
//        PushNotificationRequest request = new PushNotificationRequest(defaults.get("title"),
//                defaults.get("message"),
//                defaults.get("topic"));
//        return request;
//    }


}
