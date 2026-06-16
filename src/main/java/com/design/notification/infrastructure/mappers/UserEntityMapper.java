package com.design.notification.infrastructure.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.design.notification.domain.entities.User;
import com.design.notification.infrastructure.entities.UserEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserEntityMapper {
    User toDomain(UserEntity entity);
    
    UserEntity toEntity(User order);
}
