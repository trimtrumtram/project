package com.sub.subscription.dto;

import java.time.LocalDateTime;

import com.common.common.enums.EventType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionResponseDTO {

    private long id;
    private long clientId;
    private long productId;
    private EventType eventType;
    private LocalDateTime createdAt;
}
