package com.not.notification.model;

import java.time.LocalDateTime;

import com.common.common.enums.notification.NotificationStatus;
import com.common.common.enums.notification.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    private NotificationType type;
    private String recipient;
    private String title;
    private String body;
    private NotificationStatus status = NotificationStatus.PENDING;
    private LocalDateTime cratedAt = LocalDateTime.now();
}
