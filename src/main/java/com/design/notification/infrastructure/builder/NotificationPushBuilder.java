package com.design.notification.infrastructure.builder;

import org.springframework.stereotype.Component;

import com.design.notification.application.dtos.notification.NotificationRequest;
import com.design.notification.domain.builder.NotificationBuilder;
import com.design.notification.domain.entities.Notification;

@Component
public class NotificationPushBuilder implements NotificationBuilder {

    private Notification notification;

    @Override
    public void setNotification(NotificationRequest request) {
        this.notification = new Notification();
        this.notification.setTitle(request.getTitle());
        this.notification.setSubject(request.getSubject());
        this.notification.setMessage(request.getMessage());
        this.notification.setChannel(request.getChannel());
        this.notification.setStatus(request.getStatus());
        this.notification.setProvider(request.getProvider());
    }

    @Override
    public Notification build() {
        return this.notification;
    }
}

