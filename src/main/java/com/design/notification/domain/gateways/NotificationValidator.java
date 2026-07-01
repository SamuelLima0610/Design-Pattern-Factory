package com.design.notification.domain.gateways;

import com.design.notification.domain.entities.Notification;

public interface NotificationValidator {

    void validate(Notification notification);
}
