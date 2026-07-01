package com.design.notification.infrastructure.gateways.validators;

import org.springframework.stereotype.Component;

import com.design.notification.domain.entities.Notification;
import com.design.notification.domain.gateways.NotificationValidator;

@Component
public class EmailNotificationValidator implements NotificationValidator {

    @Override
    public void validate(Notification notification) {
        if (notification.getMessage() == null || notification.getMessage().isBlank()) {
            throw new IllegalArgumentException("Mensagem não pode ser vazia");
        }
        if (notification.getUser() == null) {
            throw new IllegalArgumentException("Usuário é obrigatório");
        }
        String email = notification.getUser().getEmail();
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("E-mail do destinatário é obrigatório");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("E-mail do destinatário é inválido: " + email);
        }
    }
}
