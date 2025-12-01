package com.not.notification;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.not.notification.dto.NotificationRequest;
import com.not.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationService service;
    private final ObjectMapper mapper;

    @KafkaListener(topics = "notification-topic", groupId = "notification")
    public void consume(String notificationJson) {

        try {
            NotificationRequest req = mapper.readValue(notificationJson, NotificationRequest.class);
            service.send(req);

        } catch (Exception e) {
            throw new  RuntimeException("Ошибка обработки JSON" + e.getMessage());
        }
    }
}
