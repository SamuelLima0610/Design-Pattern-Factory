package com.design.notification.infrastructure.gateways.senders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.design.notification.domain.entities.Notification;
import com.design.notification.domain.gateways.NotificationSender;

@Component
public class SendGridNotificationSender implements NotificationSender {
    private static final Logger logger = LoggerFactory.getLogger(SendGridNotificationSender.class);

    @Override
    public void send(Notification notification) {
        logger.info("[SendGrid] Enviando e-mail para {}: {}", notification.getUser().getEmail(), notification.getMessage());
    }
}
