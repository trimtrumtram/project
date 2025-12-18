package com.sub.subscription;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.sub.subscription.dto.event.ProductChangeEvent;
import com.sub.subscription.model.EventType;
import com.sub.subscription.model.Subscription;
import com.sub.subscription.service.SubscriptionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductEventConsumer {

    private final SubscriptionService subscriptionService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "product-events", groupId = "subscription-group")
    public void consume(ProductChangeEvent event) {
        log.info("Received product change event: {}", event);

        for (String eventTypeStr : event.getEventTypes()) {
            try {
                EventType eventType = EventType.valueOf(eventTypeStr);
                List<Subscription> subscriptions = subscriptionService.getSubscriptionsByProductIdAndEventType(event.getProductId(), eventType);

                for (Subscription subscription : subscriptions) {
                    String message = String.format("Product %d changed: %s for client %d",
                            event.getProductId(), eventType.getDescription(), subscription.getClientId());
                    kafkaTemplate.send("notification-topic", message);
                    log.info("Sent notification for subscription: {}", subscription.getId());
                }
            } catch (IllegalArgumentException e) {
                log.error("Unknown event type: {}", eventTypeStr);
            }
        }
    }
}
