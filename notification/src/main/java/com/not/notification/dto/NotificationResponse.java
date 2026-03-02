package com.not.notification.dto;

import java.time.LocalDateTime;

import com.common.common.enums.notification.NotificationType;
import com.common.common.enums.notification.NotificationStatus;

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
