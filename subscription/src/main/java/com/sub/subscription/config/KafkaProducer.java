package com.sub.subscription.config;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.sub.subscription.dto.SubscriptionResponseDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> temp;

    public void sendNotification(SubscriptionResponseDTO dto) {
        try {
            String message = String.format("New subscription created: Client %d subscribed to product %d for event %s",
                    dto.getClientId(), dto.getProductId(), dto.getEventType());
            temp.send("notification-topic", message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send notification", e);
        }
    }
}
