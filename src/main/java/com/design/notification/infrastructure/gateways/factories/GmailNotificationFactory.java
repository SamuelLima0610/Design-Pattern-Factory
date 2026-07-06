package com.design.notification.infrastructure.gateways.factories;

import org.springframework.stereotype.Component;

import com.design.notification.domain.builder.NotificationBuilder;
import com.design.notification.domain.gateways.NotificationAbstractFactory;
import com.design.notification.domain.gateways.NotificationFormatter;
import com.design.notification.domain.gateways.NotificationSender;
import com.design.notification.domain.gateways.NotificationValidator;
import com.design.notification.infrastructure.builder.NotificationEmailBuilder;
import com.design.notification.infrastructure.gateways.formatters.EmailNotificationFormatter;
import com.design.notification.infrastructure.gateways.senders.GmailNotificationSender;
import com.design.notification.infrastructure.gateways.validators.EmailNotificationValidator;

@Component
public class GmailNotificationFactory implements NotificationAbstractFactory {

    private final GmailNotificationSender sender;
    private final EmailNotificationFormatter formatter;
    private final EmailNotificationValidator validator;
    private final NotificationEmailBuilder builder;

    public GmailNotificationFactory(
            GmailNotificationSender sender,
            EmailNotificationFormatter formatter,
            EmailNotificationValidator validator,
            NotificationEmailBuilder builder) {
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
