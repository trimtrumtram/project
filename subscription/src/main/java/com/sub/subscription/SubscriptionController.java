package com.sub.subscription;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sub.subscription.dto.SubscriptionCreateDTO;
import com.sub.subscription.dto.SubscriptionResponseDTO;
import com.sub.subscription.dto.SubscriptionUpdateDTO;
import com.sub.subscription.service.SubscriptionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public SubscriptionResponseDTO createSubscription(@RequestBody SubscriptionCreateDTO dto) {
        return subscriptionService.createSub(dto);
    }

    /*@GetMapping("/{id}")
    public SubscriptionResponseDTO getSubscriptionById(@PathVariable Long id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found"));
        return subscriptionMapper.toDto(subscription);
    }

    @GetMapping
    public List<SubscriptionResponseDTO> getAllSubscriptions() {
        return subscriptionRepository.findAll()
                .stream()
                .map(subscriptionMapper::toDto)
                .toList();
    }*/

    @GetMapping("/client/{clientId}")
    public List<SubscriptionResponseDTO> getSubscriptionsByClientId(@PathVariable Long clientId) {
        return subscriptionService.getSubscriptionByClientId(clientId);
    }

    @PutMapping("/{id}")
    public SubscriptionResponseDTO updateSubscription(@PathVariable Long id, @RequestBody SubscriptionUpdateDTO dto) {
        return subscriptionService.updateSubscription(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteSubscription(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
    }
}
