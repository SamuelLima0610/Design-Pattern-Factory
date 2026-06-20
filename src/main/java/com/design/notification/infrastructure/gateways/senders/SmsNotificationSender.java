package com.design.notification.infrastructure.gateways.senders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.design.notification.domain.entities.Notification;
import com.design.notification.domain.gateways.NotificationSender;

@Component
public class SmsNotificationSender implements NotificationSender {
    private static final Logger logger = LoggerFactory.getLogger(SmsNotificationSender.class);

    @Override
    public void send(Notification notification) {
        logger.info("Enviando SMS para {}: {}", notification.getUser().getPhoneNumber(), notification.getMessage());
        // Lógica real de envio de SMS aqui
    }
}
