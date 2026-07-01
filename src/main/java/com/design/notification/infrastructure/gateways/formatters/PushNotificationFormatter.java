package com.design.notification.infrastructure.gateways.formatters;

import org.springframework.stereotype.Component;

import com.design.notification.domain.entities.Notification;
import com.design.notification.domain.gateways.NotificationFormatter;

@Component
public class PushNotificationFormatter implements NotificationFormatter {

    @Override
    public String format(Notification notification) {
        return String.format(
            "Push para %s: %s",
            notification.getUser().getName(),
            notification.getMessage()
        );
    }
}
