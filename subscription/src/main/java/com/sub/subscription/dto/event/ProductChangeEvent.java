package com.sub.subscription.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductChangeEvent {
    private Long productId;
    private List<String> eventTypes;
}
