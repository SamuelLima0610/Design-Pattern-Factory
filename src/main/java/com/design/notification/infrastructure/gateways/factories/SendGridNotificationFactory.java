package com.design.notification.infrastructure.gateways.factories;

import org.springframework.stereotype.Component;

import com.design.notification.domain.gateways.NotificationAbstractFactory;
import com.design.notification.domain.gateways.NotificationFormatter;
import com.design.notification.domain.gateways.NotificationSender;
import com.design.notification.domain.gateways.NotificationValidator;
import com.design.notification.infrastructure.gateways.formatters.EmailNotificationFormatter;
import com.design.notification.infrastructure.gateways.senders.SendGridNotificationSender;
import com.design.notification.infrastructure.gateways.validators.EmailNotificationValidator;

@Component
public class SendGridNotificationFactory implements NotificationAbstractFactory {

    private final SendGridNotificationSender sender;
    private final EmailNotificationFormatter formatter;
    private final EmailNotificationValidator validator;

    public SendGridNotificationFactory(
            SendGridNotificationSender sender,
            EmailNotificationFormatter formatter,
            EmailNotificationValidator validator) {
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
