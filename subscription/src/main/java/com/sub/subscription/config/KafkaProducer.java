package com.sub.subscription.config;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.sub.subscription.dto.SubscriptionResponseDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, Object> temp;

    public void sendNotification(SubscriptionResponseDTO dto) {
        temp.send("notification-topic", dto);
    }
}
