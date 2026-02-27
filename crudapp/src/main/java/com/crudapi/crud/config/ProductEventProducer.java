package com.crudapi.crud.config;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.common.common.events.ProductChangeEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductEventProducer {

    private final KafkaTemplate<String, ProductChangeEvent> temp;

    public void sendProductChangeEvent(ProductChangeEvent event) {
        try {
            temp.send("product-events", String.valueOf(event.getProductId()), event);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send product change event", e);
        }
    }
}
