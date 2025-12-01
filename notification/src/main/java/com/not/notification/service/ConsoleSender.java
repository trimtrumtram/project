package com.not.notification.service;

import org.springframework.stereotype.Service;

import com.not.notification.dto.NotificationRequest;
import com.not.notification.model.NotificationType;

@Service
public class ConsoleSender implements NotificationSender{

    @Override
    public boolean supports(NotificationType type) {
        return type == NotificationType.CONSOLE;
    }

    @Override
    public void send(NotificationRequest req) {
        System.out.println("Sending notification");
    }
}
