package com.design.notification.application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.design.notification.application.dtos.notification.NotificationRequest;
import com.design.notification.application.dtos.notification.NotificationResponse;
import com.design.notification.application.mappers.NotificationDtoMapper;
import com.design.notification.domain.gateways.NotificationPublisher;
import com.design.notification.domain.usecases.notification.CreateNotificationUseCase;
import com.design.notification.domain.usecases.notification.DeleteNotificationUseCase;
import com.design.notification.domain.usecases.notification.GetNotificationUseCase;
import com.design.notification.domain.usecases.notification.ListAllNotificationsUseCase;
import com.design.notification.domain.usecases.user.GetUserUseCase;

@Service
public class NotificationService {
    

    private final CreateNotificationUseCase createNotificationCase;
    private final DeleteNotificationUseCase deleteNotificationCase;
    private final GetNotificationUseCase getNotificationCase;
    private final ListAllNotificationsUseCase listNotificationsCase;
    private final NotificationPublisher notificationPublisher;
    private final GetUserUseCase getUserCase;
    private final NotificationDtoMapper notificationMapper;

    public NotificationService(
            CreateNotificationUseCase createNotificationCase, 
            DeleteNotificationUseCase deleteNotificationCase, 
            GetNotificationUseCase getNotificationCase, 
            ListAllNotificationsUseCase listNotificationsCase, 
            NotificationPublisher notificationPublisher,
            GetUserUseCase getUserCase,
            NotificationDtoMapper notificationMapper) {
        this.createNotificationCase = createNotificationCase;
        this.deleteNotificationCase = deleteNotificationCase;
        this.getNotificationCase = getNotificationCase;
        this.listNotificationsCase = listNotificationsCase;
        this.notificationPublisher = notificationPublisher;
        this.getUserCase = getUserCase;
        this.notificationMapper = notificationMapper;
    }

    public NotificationResponse createNotification(NotificationRequest request) {
        var notification = notificationMapper.toEntity(request);
        var user = getUserCase.execute(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + request.getUserId()));
        notification.setUser(user);
        notification = createNotificationCase.execute(notification);
        notificationPublisher.publish(notification);
        return notificationMapper.toResponse(notification);
    }

    public void deleteNotification(Long id) {
        deleteNotificationCase.execute(id);
    }

    public Optional<NotificationResponse> getNotificationById(Long id) {
        var notification = getNotificationCase.execute(id);
        return notification.map(notificationMapper::toResponse);
    }

    public void updateNotification(Long id, NotificationRequest request) {
        var updatedNotification = notificationMapper.toEntity(request);
        var user = getUserCase.execute(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + request.getUserId()));
        updatedNotification.setUser(user);
        getNotificationCase.execute(id).ifPresent(notification -> {
            updatedNotification.setId(notification.getId());
            createNotificationCase.execute(updatedNotification);
        });
    }

    public List<NotificationResponse> listAllNotifications() {
        var notifications = listNotificationsCase.execute();
        return notifications.stream().map(notificationMapper::toResponse).toList();
    }
}
