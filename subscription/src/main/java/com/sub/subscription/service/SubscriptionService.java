package com.sub.subscription.service;

import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.sub.subscription.model.Subscription;

import jakarta.transaction.Transactional;

import com.sub.subscription.SubscriptionMapper;
import com.sub.subscription.SubscriptionRepository;
import com.sub.subscription.config.KafkaProducer;
import com.sub.subscription.dto.SubscriptionCreateDTO;
import com.sub.subscription.dto.SubscriptionResponseDTO;
import com.sub.subscription.dto.SubscriptionUpdateDTO;
import com.common.common.enums.EventType;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository repo;
    private final SubscriptionMapper mapper;
    private final CrmValidationService crm;
    private final KafkaProducer producer;

    @Transactional
    public SubscriptionResponseDTO createSub(SubscriptionCreateDTO dto) {

        if(!crm.clientExists(dto.getClientId())) {
            throw new RuntimeException("Client not found");
        }
        if(!crm.productExists(dto.getProductId())) {
            throw new RuntimeException("Product not found");
        }

        if(repo.existsByClientIdAndProductIdAndEventType(dto.getClientId(),  dto.getProductId(), dto.getEventType())) {
            throw new RuntimeException("Subscription already exists");
        }

        Subscription subscription = mapper.toEntity(dto);
        subscription.setCreatedAt(LocalDateTime.now());

        SubscriptionResponseDTO saved = mapper.toDto(repo.save(subscription));
        producer.sendNotification(saved);

        return saved;
    }

    public void deleteSubscriptionByClientId(long clientId) {
        repo.deleteByClientId(clientId);
    }


    public void deleteSubscription(Long id) {

        Subscription subscription = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found"));

        SubscriptionResponseDTO dto = mapper.toDto(subscription);

        repo.deleteById(id);
        producer.sendNotification(dto);
    }

    public List<SubscriptionResponseDTO> getSubscriptionByClientId(Long clientId) {
        return repo.findByClientId(clientId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public List<SubscriptionResponseDTO> getSubscriptionsByProductIdAndEventType(Long productId, EventType eventType) {
        return repo.findByProductIdAndEventType(productId, eventType);
    }

    @Transactional
    public SubscriptionResponseDTO updateSubscription(Long id, SubscriptionUpdateDTO dto) {
        Subscription subscription = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found"));

        if(!crm.clientExists(dto.getClientId())) {
            throw new RuntimeException("Client not found");
        }
        if(!crm.productExists(dto.getProductId())) {
            throw new RuntimeException("Product not found");
        }

        mapper.updateSubscriptionFromDto(dto, subscription);
        Subscription saved = repo.save(subscription);
        SubscriptionResponseDTO response = mapper.toDto(saved);
        producer.sendNotification(response);

        return response;
    }
}
