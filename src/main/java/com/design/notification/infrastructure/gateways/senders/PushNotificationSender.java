package com.design.notification.infrastructure.gateways.senders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.design.notification.domain.entities.Notification;
import com.design.notification.domain.gateways.NotificationSender;

@Component
public class PushNotificationSender implements NotificationSender {
    private static final Logger logger = LoggerFactory.getLogger(PushNotificationSender.class);

    @Override
    public void send(Notification notification) {
        logger.info("Enviando Push Notification para o usuário {}: {}", notification.getUser().getName(), notification.getMessage());
        // Lógica real de envio de Push aqui
    }
}
