package com.design.notification.application.mappers;

import com.design.notification.application.dtos.UserRequest;
import com.design.notification.application.dtos.UserResponse;
import com.design.notification.domain.entities.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-16T11:42:09-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Ubuntu)"
)
@Component
public class UserDtoMapperImpl implements UserDtoMapper {

    @Override
    public UserResponse toResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        userResponse.setId( user.getId() );
        userResponse.setName( user.getName() );
        userResponse.setPhoneNumber( user.getPhoneNumber() );
        userResponse.setEmail( user.getEmail() );
        userResponse.setCreatedAt( user.getCreatedAt() );
        userResponse.setUpdatedAt( user.getUpdatedAt() );

        return userResponse;
    }

    @Override
    public User toEntity(UserRequest userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        User user = new User();

        user.setName( userRequest.getName() );
        user.setPhoneNumber( userRequest.getPhoneNumber() );
        user.setEmail( userRequest.getEmail() );

        return user;
    }
}
