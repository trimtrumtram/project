package com.sub.subscription.consumers;

import com.common.common.enums.EventType;
import com.sub.subscription.dto.SubscriptionResponseDTO;
import com.sub.subscription.dto.event.NotificationEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.common.common.events.ProductChangeEvent;
import com.sub.subscription.service.SubscriptionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductEventConsumer {

    private final SubscriptionService subscriptionService;
    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    @KafkaListener(topics = "product-events", groupId = "subscription-group")
    public void consume(ProductChangeEvent event) {
        log.info("Received product change event: {}", event);
        if (event == null || event.getProductId() == null || event.getEventTypes() == null) {
            log.error("Invalid product change event payload: {}", event);
            return;
        }
        Long productId = event.getProductId();
        List<String> eventTypes = event.getEventTypes();

        for (String eventTypeStr : eventTypes) {
            try {
                EventType eventType = EventType.valueOf(eventTypeStr);
                List<SubscriptionResponseDTO> subscriptions = subscriptionService.getSubscriptionsByProductIdAndEventType(productId, eventType);

                for (SubscriptionResponseDTO dto : subscriptions) {
                    NotificationEvent notification = NotificationEvent.builder()
                            .type("CONSOLE")
                            .recipient("client-" + dto.getClientId())
                            .title("Product Update")
                            .body(String.format("Product %d changed: %s for client %d",
                                    productId, eventType.getDescription(), dto.getClientId()))
                            .build();
                    kafkaTemplate.send("notification-topic", String.valueOf(dto.getClientId()), notification);
                    log.info("Sent notification for subscription: {}", dto.getId());
                }
            } catch (IllegalArgumentException e) {
                log.error("Unknown event type: {}", eventTypeStr);
            }
        }
    }
}
