package com.sub.subscription.config;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.sub.subscription.dto.event.NotificationEvent;
import com.sub.subscription.dto.SubscriptionResponseDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, NotificationEvent> temp;

    public void sendNotification(SubscriptionResponseDTO dto) {
        try {
            NotificationEvent event = NotificationEvent.builder()
                    .type("CONSOLE")
                    .recipient("client-" + dto.getClientId())
                    .title("Subscription Event")
                    .body(String.format("Client %d subscribed to product %d for event %s",
                            dto.getClientId(), dto.getProductId(), dto.getEventType()))
                    .build();
            temp.send("notification-topic", String.valueOf(dto.getClientId()), event);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to send notification", ex);
        }
    }
}
