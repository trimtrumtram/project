package com.not.notification.service;

import org.springframework.stereotype.Service;

import com.not.notification.dto.NotificationRequest;
import com.common.common.enums.notification.NotificationType;

@Service
public class SmsSender implements NotificationSender {

    @Override
    public void send(NotificationRequest req) {
        System.out.println("Sending sms to: " + req.getRecipient());
    }

    @Override
    public NotificationType getType() {
        return NotificationType.SMS;
    }

}
