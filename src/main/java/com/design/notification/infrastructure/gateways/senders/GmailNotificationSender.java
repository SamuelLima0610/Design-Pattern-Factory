package com.design.notification.infrastructure.gateways.senders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.design.notification.domain.entities.Notification;
import com.design.notification.domain.gateways.NotificationSender;

@Component
public class GmailNotificationSender implements NotificationSender {
    private static final Logger logger = LoggerFactory.getLogger(GmailNotificationSender.class);

    @Override
    public void send(Notification notification) {
        logger.info("[Gmail] Enviando e-mail para {}: {}", notification.getUser().getEmail(), notification.getMessage());
    }
}
