package com.not.notification.dto;

import java.time.LocalDateTime;

import com.not.notification.model.NotificationStatus;
import com.not.notification.model.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationResponse {

    private NotificationType type;
    private String recipient;
    private String title;
    private String body;
    private NotificationStatus status;
    private LocalDateTime creaetedAt;
}
