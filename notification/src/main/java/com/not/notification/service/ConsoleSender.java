package com.not.notification.service;

import org.springframework.stereotype.Service;

import com.not.notification.dto.NotificationRequest;
import com.common.common.enums.notification.NotificationType;

@Service
public class ConsoleSender implements NotificationSender{

    @Override
    public NotificationType getType() {
        return NotificationType.CONSOLE;
    }

    @Override
    public void send(NotificationRequest req) {
        System.out.println("Notification to " + req.getRecipient() + ": " + req.getBody());
    }
}
