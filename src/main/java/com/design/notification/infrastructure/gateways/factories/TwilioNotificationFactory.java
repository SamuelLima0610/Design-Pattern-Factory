package com.design.notification.infrastructure.gateways.factories;

import org.springframework.stereotype.Component;

import com.design.notification.domain.builder.NotificationBuilder;
import com.design.notification.domain.gateways.NotificationAbstractFactory;
import com.design.notification.domain.gateways.NotificationFormatter;
import com.design.notification.domain.gateways.NotificationSender;
import com.design.notification.domain.gateways.NotificationValidator;
import com.design.notification.infrastructure.builder.NotificationSmsBuilder;
import com.design.notification.infrastructure.gateways.formatters.SmsNotificationFormatter;
import com.design.notification.infrastructure.gateways.senders.TwilioNotificationSender;
import com.design.notification.infrastructure.gateways.validators.SmsNotificationValidator;

@Component
public class TwilioNotificationFactory implements NotificationAbstractFactory {

    private final TwilioNotificationSender sender;
    private final SmsNotificationFormatter formatter;
    private final SmsNotificationValidator validator;
    private final NotificationSmsBuilder builder;

    public TwilioNotificationFactory(
            TwilioNotificationSender sender,
            SmsNotificationFormatter formatter,
            SmsNotificationValidator validator,
            NotificationSmsBuilder builder) {
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
        // TODO Auto-generated method stub
        return builder;
    }
}
