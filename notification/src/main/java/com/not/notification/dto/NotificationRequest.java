package com.not.notification.dto;

import com.not.notification.model.NotificationType;

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
