package com.not.notification.dto;

import com.common.common.enums.notification.NotificationType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotificationRequest {

    private NotificationType type;
    private String recipient;
    private String title;
    private String body;
}
