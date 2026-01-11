package com.crudapi.crud.config;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.crudapi.crud.dto.ProductChangeEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendProductChangeEvent(ProductChangeEvent event) {
        try {
            String message = event.getProductId() + ":" + String.join(",", event.getEventTypes());
            kafkaTemplate.send("product-events", message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send product change event", e);
        }
    }
}
