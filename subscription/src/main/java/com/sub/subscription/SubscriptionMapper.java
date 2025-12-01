package com.sub.subscription;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.sub.subscription.dto.SubscriptionCreateDTO;
import com.sub.subscription.dto.SubscriptionResponseDTO;
import com.sub.subscription.dto.SubscriptionUpdateDTO;
import com.sub.subscription.model.Subscription;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    Subscription toEntity(SubscriptionCreateDTO dto);
    SubscriptionResponseDTO toDto(Subscription subscription);

    void updateSubscriptionFromDto(SubscriptionUpdateDTO dto, @MappingTarget Subscription subscription);
}
