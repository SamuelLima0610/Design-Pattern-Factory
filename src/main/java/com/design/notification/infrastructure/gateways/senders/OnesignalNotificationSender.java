package com.design.notification.infrastructure.gateways.senders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.design.notification.domain.entities.Notification;
import com.design.notification.domain.gateways.NotificationSender;

@Component
public class OnesignalNotificationSender implements NotificationSender {
    private static final Logger logger = LoggerFactory.getLogger(OnesignalNotificationSender.class);

    @Override
    public void send(Notification notification) {
        logger.info("[OneSignal] Enviando Push Notification para {}: {}", notification.getUser().getName(), notification.getMessage());
    }
}
