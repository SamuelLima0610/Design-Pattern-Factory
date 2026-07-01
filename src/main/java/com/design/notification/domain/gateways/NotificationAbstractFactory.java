package com.design.notification.domain.gateways;

public interface NotificationAbstractFactory {

    NotificationSender createSender();

    NotificationFormatter createFormatter();

    NotificationValidator createValidator();
}
