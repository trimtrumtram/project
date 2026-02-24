package com.not.notification;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com
import com.not.notification.dto.NotificationRequest;
import com.common.common.enums.notification.NotificationType;
import com.not.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationService service;

    @KafkaListener(topics = "notification-topic", groupId = "notification")
    public void consume(NotificationRequest req) {
        if (req.getType() == null) {
            req.setType(NotificationType.CONSOLE);
        }
        service.send(req);
    }
}
