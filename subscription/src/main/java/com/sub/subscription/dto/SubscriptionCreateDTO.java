package com.sub.subscription.dto;

import lombok.*;

import com.sub.subscription.model.EventType;

import jakarta.validation.constraints.NotNull;



@Data
@AllArgsConstructor
@Builder
public class SubscriptionCreateDTO {

    @NotNull
    private long clientId;

    @NotNull
    private long productId;

    @NotNull
    private EventType eventType;
}
