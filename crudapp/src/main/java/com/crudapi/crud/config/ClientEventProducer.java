package com.crudapi.crud.config;

import com.common.common.events.ClientDeletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientEventProducer {

    private final KafkaTemplate<String, ClientDeletedEvent> temp;

    public void sendClientDeletedEvent(ClientDeletedEvent event) {
        try {
            temp.send("client-events", String.valueOf(event.getClientId()), event);
        } catch(Exception ex) {
            throw new RuntimeException("Failed to send client deleted event", ex);
        }
    }
}
