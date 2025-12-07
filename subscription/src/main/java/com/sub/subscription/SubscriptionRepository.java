package com.sub.subscription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sub.subscription.model.Subscription;
import com.sub.subscription.model.EventType;


import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long>{

    List<Subscription> findByClientId(long clientId);
    List<Subscription> findByProductIdAndEventType(long productId, EventType eventType);
    
    boolean existsByClientIdAndProductIdAndEventType(Long clientId, Long productId, EventType eventType);
}
