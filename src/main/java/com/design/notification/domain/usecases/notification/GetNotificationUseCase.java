package com.design.notification.domain.usecases.notification;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.design.notification.domain.entities.Notification;
import com.design.notification.domain.repositories.NotificationRepository;

@Component
public class GetNotificationUseCase {
    
    private final NotificationRepository notificationRepository;

    public GetNotificationUseCase(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Optional<Notification> execute(Long id) {
        return notificationRepository.findById(id);
    }

}
