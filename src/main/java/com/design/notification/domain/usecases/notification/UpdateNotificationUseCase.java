package com.design.notification.domain.usecases.notification;

import org.springframework.stereotype.Component;

import com.design.notification.domain.entities.Notification;
import com.design.notification.domain.repositories.NotificationRepository;

@Component
public class UpdateNotificationUseCase {
 
    private final NotificationRepository notificationRepository;

    public UpdateNotificationUseCase(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void execute(Long notificationId, Notification updatedNotification) {
        var notification = notificationRepository.findById(notificationId);
        if (notification.isPresent()) {
            updatedNotification.setId(notification.get().getId());
            notificationRepository.save(updatedNotification);
        }
    }
}
