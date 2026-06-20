package com.design.notification.application.mappers;

import com.design.notification.application.dtos.notification.NotificationRequest;
import com.design.notification.application.dtos.notification.NotificationResponse;
import com.design.notification.domain.entities.Notification;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-20T13:03:56-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Ubuntu)"
)
@Component
public class NotificationDtoMapperImpl implements NotificationDtoMapper {

    @Override
    public NotificationResponse toResponse(Notification notification) {
        if ( notification == null ) {
            return null;
        }

        NotificationResponse notificationResponse = new NotificationResponse();

        notificationResponse.setId( notification.getId() );
        notificationResponse.setMessage( notification.getMessage() );
        notificationResponse.setChannel( notification.getChannel() );
        notificationResponse.setStatus( notification.getStatus() );
        notificationResponse.setCreatedAt( notification.getCreatedAt() );
        notificationResponse.setUpdatedAt( notification.getUpdatedAt() );

        return notificationResponse;
    }

    @Override
    public Notification toEntity(NotificationRequest notificationRequest) {
        if ( notificationRequest == null ) {
            return null;
        }

        Notification notification = new Notification();

        notification.setMessage( notificationRequest.getMessage() );
        notification.setChannel( notificationRequest.getChannel() );
        notification.setStatus( notificationRequest.getStatus() );

        return notification;
    }
}
