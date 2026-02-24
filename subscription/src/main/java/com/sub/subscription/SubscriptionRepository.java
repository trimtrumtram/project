package com.sub.subscription;

import com.sub.subscription.dto.SubscriptionResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sub.subscription.model.Subscription;
import com.common.common.enums.EventType;


import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long>{

    List<Subscription> findByClientId(long clientId);
    List<SubscriptionResponseDTO> findByProductIdAndEventType(long productId, EventType eventType);
    
    boolean existsByClientIdAndProductIdAndEventType(Long clientId, Long productId, EventType eventType);

    void deleteByClientId(long clientId);
}
