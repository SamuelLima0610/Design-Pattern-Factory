package com.design.notification.domain.usecases.notification;

import java.util.List;

import org.springframework.stereotype.Component;

import com.design.notification.domain.entities.Notification;
import com.design.notification.domain.repositories.NotificationRepository;

@Component
public class ListAllNotificationsUseCase {
    
    private final NotificationRepository notificationRepository;

    public ListAllNotificationsUseCase(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> execute() {
        return notificationRepository.findAll();
    }
}
