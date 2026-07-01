package com.design.notification.infrastructure.gateways.formatters;

import org.springframework.stereotype.Component;

import com.design.notification.domain.entities.Notification;
import com.design.notification.domain.gateways.NotificationFormatter;

@Component
public class EmailNotificationFormatter implements NotificationFormatter {

    @Override
    public String format(Notification notification) {
        return String.format(
            "Para: %s\nAssunto: Notificação\nMensagem: %s",
            notification.getUser().getEmail(),
            notification.getMessage()
        );
    }
}
