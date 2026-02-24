package com.not.notification.service;

import org.springframework.stereotype.Service;

import com.common.common.enums.notification.NotificationType;
import com.not.notification.dto.NotificationRequest;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    Map<NotificationType, NotificationSender> senders;


    public NotificationService(List<NotificationSender> senderList) {
        this.senders = senderList.stream()
                .collect(Collectors.toUnmodifiableMap(NotificationSender::getType, Function.identity()));
    }

    public void send(NotificationRequest request) {
        NotificationSender sender = senders.get(request.getType());
        if(sender == null) {
            throw new IllegalArgumentException("Unsupported notification type: " + request.getType());
        }
        sender.send(request);
    }
}
