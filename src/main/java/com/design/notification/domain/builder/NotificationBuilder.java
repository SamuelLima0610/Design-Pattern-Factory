package com.design.notification.domain.builder;

import com.design.notification.application.dtos.notification.NotificationRequest;
import com.design.notification.domain.entities.Notification;

public interface NotificationBuilder {

    void setNotification(NotificationRequest request);
    Notification build();
}
