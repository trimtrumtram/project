package com.crudapi.crud.config;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.crudapi.crud.dto.ProductChangeEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, ProductChangeEvent> kafkaTemplate;

    public void sendProductChangeEvent(ProductChangeEvent event) {
        try {
            kafkaTemplate.send("product-events", event);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send product change event", e);
        }
    }
}
