package com.not.notification.service;

import org.springframework.stereotype.Service;

import com.not.notification.dto.NotificationRequest;
import com.not.notification.model.NotificationType;

@Service
public class SmsSender implements NotificationSender {

    @Override
    public void send(NotificationRequest req) {
        System.out.println("Sending sms to: " + req.getRecipient());
    }

    @Override
    public boolean supports(NotificationType type) {
        return type == NotificationType.SMS;
    }

}
