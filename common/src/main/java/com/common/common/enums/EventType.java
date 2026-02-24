package com.common.common.enums;

import lombok.Getter;

@Getter
public enum EventType {
    PRICE_CHANGE("Изменение цены"),
    DESCRIPTION_UPDATE("Обновление описания"),
    NEW_NAME("Новая версия");

    
    private final String description;

    EventType(String description) {
        this.description = description;
    }
}
