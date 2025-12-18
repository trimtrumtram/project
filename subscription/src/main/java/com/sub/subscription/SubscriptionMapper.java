package com.sub.subscription;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.sub.subscription.dto.SubscriptionCreateDTO;
import com.sub.subscription.dto.SubscriptionResponseDTO;
import com.sub.subscription.dto.SubscriptionUpdateDTO;
import com.sub.subscription.model.Subscription;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Subscription toEntity(SubscriptionCreateDTO dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "createdAt", source = "createdAt")
    SubscriptionResponseDTO toDto(Subscription subscription);

    void updateSubscriptionFromDto(SubscriptionUpdateDTO dto, @MappingTarget Subscription subscription);
}
