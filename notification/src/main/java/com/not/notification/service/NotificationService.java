package com.not.notification.service;

import org.springframework.stereotype.Service;

import com.not.notification.dto.NotificationRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final ConsoleSender consoleSender;
    private final SmsSender smsSender;

    public void send(NotificationRequest req) {

        try {
            switch(req.getType()) {
                case SMS -> smsSender.send(req);
                case CONSOLE -> consoleSender.send(req);
            }
        } catch(Exception e) {
            throw new RuntimeException("Failed");
        }
    }
}
