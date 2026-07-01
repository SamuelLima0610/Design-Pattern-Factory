package com.design.notification.domain.gateways;

import com.design.notification.domain.entities.Notification;

public interface NotificationFormatter {

    String format(Notification notification);
}
