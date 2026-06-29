package com.design.notification.application.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.design.notification.application.dtos.notification.NotificationRequest;
import com.design.notification.application.dtos.notification.NotificationResponse;
import com.design.notification.domain.entities.Notification;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationDtoMapper {

    @Mapping(target = "userId", source = "user.id")
    NotificationResponse toResponse(Notification notification);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    Notification toEntity(NotificationRequest notificationRequest);
}
