package com.design.notification.infrastructure.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.design.notification.domain.entities.Notification;
import com.design.notification.infrastructure.entities.NotificationEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationEntityMapper {
    
    Notification toDomain(NotificationEntity entity);
    NotificationEntity toEntity(Notification notification);
}
