package com.design.notification.domain.usecases.notification;

import org.springframework.stereotype.Component;

import com.design.notification.domain.repositories.NotificationRepository;

@Component
public class DeleteNotificationUseCase {
    
    private final NotificationRepository notificationRepository;

    public DeleteNotificationUseCase(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }  

    public void execute(Long id){
        notificationRepository.deleteById(id);
    }
}
