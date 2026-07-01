package com.design.notification.infrastructure.gateways.factories;

import org.springframework.stereotype.Component;

import com.design.notification.domain.gateways.NotificationAbstractFactory;
import com.design.notification.domain.gateways.NotificationFormatter;
import com.design.notification.domain.gateways.NotificationSender;
import com.design.notification.domain.gateways.NotificationValidator;
import com.design.notification.infrastructure.gateways.formatters.PushNotificationFormatter;
import com.design.notification.infrastructure.gateways.senders.OnesignalNotificationSender;
import com.design.notification.infrastructure.gateways.validators.PushNotificationValidator;

@Component
public class OnesignalNotificationFactory implements NotificationAbstractFactory {

    private final OnesignalNotificationSender sender;
    private final PushNotificationFormatter formatter;
    private final PushNotificationValidator validator;

    public OnesignalNotificationFactory(
            OnesignalNotificationSender sender,
            PushNotificationFormatter formatter,
            PushNotificationValidator validator) {
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
