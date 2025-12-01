package com.sub.subscription.model;

import lombok.Getter;

@Getter
public enum EventType {
    PRICE_CHANGE("Изменение цены"),
    STOCK_UPDATE("Обновление наличия"),
    NEW_VERSION("Новая версия");

    
    private final String description;

    EventType(String description) {
        this.description = description;
    }
}
