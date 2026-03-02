package com.not.notification.service;

import org.springframework.stereotype.Service;

import com.not.notification.dto.NotificationRequest;
import com.common.common.enums.notification.NotificationType;

@Service
public interface NotificationSender {

    void send(NotificationRequest req);
    NotificationType getType();
}
