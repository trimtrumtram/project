package com.sub.subscription;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.sub.subscription.model.EventType;
import com.sub.subscription.model.Subscription;
import com.sub.subscription.service.SubscriptionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductEventConsumer {

    private final SubscriptionService subscriptionService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "product-events", groupId = "subscription-group")
    public void consume(String message) {
        log.info("Received product change event: {}", message);

        String[] parts = message.split(":");
        if (parts.length != 2) {
            log.error("Invalid message format: {}", message);
            return;
        }

        Long productId;
        try {
            productId = Long.parseLong(parts[0]);
        } catch (NumberFormatException e) {
            log.error("Invalid productId: {}", parts[0]);
            return;
        }

        List<String> eventTypes = Arrays.asList(parts[1].split(","));

        for (String eventTypeStr : eventTypes) {
            try {
                EventType eventType = EventType.valueOf(eventTypeStr);
                List<Subscription> subscriptions = subscriptionService.getSubscriptionsByProductIdAndEventType(productId, eventType);

                for (Subscription subscription : subscriptions) {
                    String notificationMessage = String.format("Product %d changed: %s for client %d",
                            productId, eventType.getDescription(), subscription.getClientId());
                    kafkaTemplate.send("notification-topic", notificationMessage);
                    log.info("Sent notification for subscription: {}", subscription.getId());
                }
            } catch (IllegalArgumentException e) {
                log.error("Unknown event type: {}", eventTypeStr);
            }
        }
    }
}
