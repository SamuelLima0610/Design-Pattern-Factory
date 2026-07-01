package com.design.notification.infrastructure.gateways.validators;

import org.springframework.stereotype.Component;

import com.design.notification.domain.entities.Notification;
import com.design.notification.domain.gateways.NotificationValidator;

@Component
public class WhatsAppNotificationValidator implements NotificationValidator {

    @Override
    public void validate(Notification notification) {
        if (notification.getMessage() == null || notification.getMessage().isBlank()) {
            throw new IllegalArgumentException("Mensagem não pode ser vazia");
        }
        if (notification.getUser() == null) {
            throw new IllegalArgumentException("Usuário é obrigatório");
        }
        String phoneNumber = notification.getUser().getPhoneNumber();
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Número de telefone do destinatário é obrigatório para WhatsApp");
        }
    }
}
