package com.design.notification.infrastructure.mappers;

import com.design.notification.domain.entities.User;
import com.design.notification.infrastructure.entities.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-20T13:03:56-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Ubuntu)"
)
@Component
public class UserEntityMapperImpl implements UserEntityMapper {

    @Override
    public User toDomain(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        User user = new User();

        user.setId( entity.getId() );
        user.setName( entity.getName() );
        user.setPhoneNumber( entity.getPhoneNumber() );
        user.setEmail( entity.getEmail() );
        user.setCreatedAt( entity.getCreatedAt() );
        user.setUpdatedAt( entity.getUpdatedAt() );

        return user;
    }

    @Override
    public UserEntity toEntity(User order) {
        if ( order == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setId( order.getId() );
        userEntity.setName( order.getName() );
        userEntity.setPhoneNumber( order.getPhoneNumber() );
        userEntity.setEmail( order.getEmail() );
        userEntity.setCreatedAt( order.getCreatedAt() );
        userEntity.setUpdatedAt( order.getUpdatedAt() );

        return userEntity;
    }
}
