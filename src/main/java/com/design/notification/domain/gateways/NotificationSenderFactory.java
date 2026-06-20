package com.design.notification.domain.gateways;

import com.design.notification.domain.enums.NotificationChannel;

public interface NotificationSenderFactory {
    NotificationSender getSender(NotificationChannel channel);
}
