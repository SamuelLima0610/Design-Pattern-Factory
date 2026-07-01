package com.design.notification.infrastructure.gateways.factories;

import org.springframework.stereotype.Component;

import com.design.notification.domain.gateways.NotificationAbstractFactory;
import com.design.notification.domain.gateways.NotificationFormatter;
import com.design.notification.domain.gateways.NotificationSender;
import com.design.notification.domain.gateways.NotificationValidator;
import com.design.notification.infrastructure.gateways.formatters.WhatsAppNotificationFormatter;
import com.design.notification.infrastructure.gateways.senders.WhatsAppApiNotificationSender;
import com.design.notification.infrastructure.gateways.validators.WhatsAppNotificationValidator;

@Component
public class WhatsAppApiNotificationFactory implements NotificationAbstractFactory {

    private final WhatsAppApiNotificationSender sender;
    private final WhatsAppNotificationFormatter formatter;
    private final WhatsAppNotificationValidator validator;

    public WhatsAppApiNotificationFactory(
            WhatsAppApiNotificationSender sender,
            WhatsAppNotificationFormatter formatter,
            WhatsAppNotificationValidator validator) {
        this.sender = sender;
        this.formatter = formatter;
        this.validator = validator;
    }

    @Override
    public NotificationSender createSender() {
        return sender;
    }

    @Override
    public NotificationFormatter createFormatter() {
        return formatter;
    }

    @Override
    public NotificationValidator createValidator() {
        return validator;
    }
}
