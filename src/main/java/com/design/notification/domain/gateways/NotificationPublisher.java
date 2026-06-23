package com.design.notification.domain.gateways;

import com.design.notification.domain.entities.Notification;

public interface NotificationPublisher {
    void publish(Notification notification);
}
