package com.sub.subscription.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.common.common.enums.EventType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subscriptions")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "client_id", nullable = false)
    private long clientId;

    @Column(name = "product_id", nullable = false)
    private long productId;

    @Column(name = "event_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
