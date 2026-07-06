package com.design.notification.infrastructure.gateways.factories;

import org.springframework.stereotype.Component;

import com.design.notification.domain.builder.NotificationBuilder;
import com.design.notification.domain.gateways.NotificationAbstractFactory;
import com.design.notification.domain.gateways.NotificationFormatter;
import com.design.notification.domain.gateways.NotificationSender;
import com.design.notification.domain.gateways.NotificationValidator;
import com.design.notification.infrastructure.builder.NotificationWhatsAppBuilder;
import com.design.notification.infrastructure.gateways.formatters.WhatsAppNotificationFormatter;
import com.design.notification.infrastructure.gateways.senders.WhatsAppApiNotificationSender;
import com.design.notification.infrastructure.gateways.validators.WhatsAppNotificationValidator;

@Component
public class WhatsAppApiNotificationFactory implements NotificationAbstractFactory {

    private final WhatsAppApiNotificationSender sender;
    private final WhatsAppNotificationFormatter formatter;
    private final WhatsAppNotificationValidator validator;
    private final NotificationWhatsAppBuilder builder;

    public WhatsAppApiNotificationFactory(
            WhatsAppApiNotificationSender sender,
            WhatsAppNotificationFormatter formatter,
            WhatsAppNotificationValidator validator,
            NotificationWhatsAppBuilder builder) {
        this.sender = sender;
        this.formatter = formatter;
        this.validator = validator;
        this.builder = builder;
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

    @Override
    public NotificationBuilder createBuilder() {
        return builder;
    }

}
