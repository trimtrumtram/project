package com.sub.subscription.consumers;

import com.sub.subscription.dto.event.ClientDeletedEvent;
import com.sub.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientEventConsumer {

    private final SubscriptionService service;

    @KafkaListener(
            topics = "client-events",
            groupId = "subscription-group")
    public void consume(ClientDeletedEvent event) {
        if(event == null || event.getClientId() == null) {
            return;
        }

        service.deleteSubscriptionByClientId(event.getClientId());
    }
}
