package com.sub.subscription.dto;

import com.sub.subscription.model.EventType;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubscriptionUpdateDTO {

    @NotNull
    private long clientId;

    @NotNull
    private long productId;

    @NotNull
    private EventType eventType;
}
