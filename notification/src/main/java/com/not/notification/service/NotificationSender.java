package com.not.notification.service;

import org.springframework.stereotype.Service;

import com.not.notification.dto.NotificationRequest;
import com.not.notification.model.NotificationType;

@Service
public interface NotificationSender {

    void send(NotificationRequest req);
    boolean supports(NotificationType type);
}
