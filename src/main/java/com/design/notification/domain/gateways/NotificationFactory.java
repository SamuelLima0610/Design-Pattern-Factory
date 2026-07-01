package com.design.notification.domain.gateways;

import com.design.notification.domain.enums.NotificationProvider;

public interface NotificationFactory {

    NotificationAbstractFactory getFactory(NotificationProvider provider);
}
