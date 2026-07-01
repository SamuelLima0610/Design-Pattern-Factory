package com.design.notification.infrastructure.gateways.validators;

import org.springframework.stereotype.Component;

import com.design.notification.domain.entities.Notification;
import com.design.notification.domain.gateways.NotificationValidator;

@Component
public class PushNotificationValidator implements NotificationValidator {

    @Override
    public void validate(Notification notification) {
        if (notification.getMessage() == null || notification.getMessage().isBlank()) {
            throw new IllegalArgumentException("Mensagem não pode ser vazia");
        }
        if (notification.getUser() == null) {
            throw new IllegalArgumentException("Usuário é obrigatório");
        }
        if (notification.getUser().getName() == null || notification.getUser().getName().isBlank()) {
            throw new IllegalArgumentException("Nome do destinatário é obrigatório para Push Notification");
        }
    }
}
