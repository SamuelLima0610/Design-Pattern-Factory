package com.design.notification.domain.usecases.notification;

import org.springframework.stereotype.Service;

import com.design.notification.domain.entities.Notification;
import com.design.notification.domain.enums.NotificationStatus;
import com.design.notification.domain.gateways.NotificationAbstractFactory;
import com.design.notification.domain.gateways.NotificationFactory;
import com.design.notification.domain.repositories.NotificationRepository;

@Service
public class SendNotificationUseCase {

    private final NotificationFactory notificationFactory;
    private final NotificationRepository notificationRepository;

    public SendNotificationUseCase(NotificationFactory notificationFactory, NotificationRepository notificationRepository) {
        this.notificationFactory = notificationFactory;
        this.notificationRepository = notificationRepository;
    }

    public void execute(Notification notification) {
        try {
            NotificationAbstractFactory factory = notificationFactory.getFactory(notification.getProvider());
            factory.createValidator().validate(notification);
            factory.createSender().send(notification);
            notification.setStatus(NotificationStatus.SENT);
        } catch (Exception e) {
            notification.setStatus(NotificationStatus.FAILED);
            throw e;
        } finally {
            notificationRepository.save(notification);
        }
    }
}
