package com.sub.subscription.dto;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.common.common.enums.EventType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SubscriptionRequestDTO {

    @NotNull
    private long id;

    @NotNull
    private long clientId;

    @NotNull
    private long productId;

    @NotNull
    private EventType eventType;

    @CreationTimestamp
    @NotNull
    private LocalDateTime createdAt;
}
