package com.design.notification.domain.gateways;

import com.design.notification.domain.builder.NotificationBuilder;

public interface NotificationAbstractFactory {

    NotificationSender createSender();

    NotificationFormatter createFormatter();

    NotificationValidator createValidator();

    NotificationBuilder createBuilder();
}
