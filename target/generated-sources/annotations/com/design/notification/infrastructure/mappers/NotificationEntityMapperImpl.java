package com.design.notification.infrastructure.mappers;

import com.design.notification.domain.entities.Notification;
import com.design.notification.domain.entities.User;
import com.design.notification.infrastructure.entities.NotificationEntity;
import com.design.notification.infrastructure.entities.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-20T13:03:56-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Ubuntu)"
)
@Component
public class NotificationEntityMapperImpl implements NotificationEntityMapper {

    @Override
    public Notification toDomain(NotificationEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Notification notification = new Notification();

        notification.setId( entity.getId() );
        notification.setMessage( entity.getMessage() );
        notification.setChannel( entity.getChannel() );
        notification.setStatus( entity.getStatus() );
        notification.setUser( userEntityToUser( entity.getUser() ) );
        notification.setCreatedAt( entity.getCreatedAt() );
        notification.setUpdatedAt( entity.getUpdatedAt() );

        return notification;
    }

    @Override
    public NotificationEntity toEntity(Notification notification) {
        if ( notification == null ) {
            return null;
        }

        NotificationEntity notificationEntity = new NotificationEntity();

        notificationEntity.setId( notification.getId() );
        notificationEntity.setMessage( notification.getMessage() );
        notificationEntity.setChannel( notification.getChannel() );
        notificationEntity.setStatus( notification.getStatus() );
        notificationEntity.setUser( userToUserEntity( notification.getUser() ) );
        notificationEntity.setCreatedAt( notification.getCreatedAt() );
        notificationEntity.setUpdatedAt( notification.getUpdatedAt() );

        return notificationEntity;
    }

    protected User userEntityToUser(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        User user = new User();

        user.setId( userEntity.getId() );
        user.setName( userEntity.getName() );
        user.setPhoneNumber( userEntity.getPhoneNumber() );
        user.setEmail( userEntity.getEmail() );
        user.setCreatedAt( userEntity.getCreatedAt() );
        user.setUpdatedAt( userEntity.getUpdatedAt() );

        return user;
    }

    protected UserEntity userToUserEntity(User user) {
        if ( user == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setId( user.getId() );
        userEntity.setName( user.getName() );
        userEntity.setPhoneNumber( user.getPhoneNumber() );
        userEntity.setEmail( user.getEmail() );
        userEntity.setCreatedAt( user.getCreatedAt() );
        userEntity.setUpdatedAt( user.getUpdatedAt() );

        return userEntity;
    }
}
