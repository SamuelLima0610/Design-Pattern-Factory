package com.design.notification.domain.usecases.notification;

import org.springframework.stereotype.Component;

import com.design.notification.domain.entities.Notification;
import com.design.notification.domain.repositories.NotificationRepository;

@Component
public class CreateNotificationUseCase {
    
    private final NotificationRepository notificationRepository;

    public CreateNotificationUseCase(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification execute(Notification notification) {
        return notificationRepository.save(notification);
    }
}

