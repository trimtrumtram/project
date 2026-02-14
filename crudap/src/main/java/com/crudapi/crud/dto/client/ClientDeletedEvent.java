package com.crudapi.crud.dto.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDeletedEvent {
    private long clientId;
}
