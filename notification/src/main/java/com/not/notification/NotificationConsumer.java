package com.not.notification;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.not.notification.dto.NotificationRequest;
import com.not.notification.model.NotificationType;
import com.not.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationService service;

    @KafkaListener(topics = "notification-topic", groupId = "notification")
    public void consume(String message) {
        System.out.println("Received notification: " + message);

        NotificationRequest req = new NotificationRequest();
        req.setType(NotificationType.CONSOLE);
        req.setRecipient("system");
        req.setTitle("Subscription Notification");
        req.setBody(message);

        service.send(req);
    }
}
