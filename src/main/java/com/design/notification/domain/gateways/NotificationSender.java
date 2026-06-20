package com.design.notification.domain.gateways;

import com.design.notification.domain.entities.Notification;

public interface NotificationSender {
    void send(Notification notification);
}
