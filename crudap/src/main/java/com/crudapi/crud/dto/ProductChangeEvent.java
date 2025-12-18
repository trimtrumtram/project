package com.crudapi.crud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductChangeEvent {
    private Long productId;
    private List<String> eventTypes; // e.g., "PRICE_CHANGE", "STOCK_UPDATE"
}
