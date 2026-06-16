package com.design.notification.application.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.design.notification.application.dtos.UserRequest;
import com.design.notification.application.dtos.UserResponse;
import com.design.notification.domain.entities.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserDtoMapper {
    
    UserResponse toResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(UserRequest userRequest);
    
}
