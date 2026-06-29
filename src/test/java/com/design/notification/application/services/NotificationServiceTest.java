package com.design.notification.application.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.design.notification.application.dtos.notification.NotificationRequest;
import com.design.notification.application.dtos.notification.NotificationResponse;
import com.design.notification.application.mappers.NotificationDtoMapper;
import com.design.notification.domain.entities.Notification;
import com.design.notification.domain.entities.User;
import com.design.notification.domain.enums.NotificationChannel;
import com.design.notification.domain.enums.NotificationProvider;
import com.design.notification.domain.enums.NotificationStatus;
import com.design.notification.domain.gateways.NotificationPublisher;
import com.design.notification.domain.usecases.notification.CreateNotificationUseCase;
import com.design.notification.domain.usecases.notification.DeleteNotificationUseCase;
import com.design.notification.domain.usecases.notification.GetNotificationUseCase;
import com.design.notification.domain.usecases.notification.ListAllNotificationsUseCase;
import com.design.notification.domain.usecases.user.GetUserUseCase;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock private CreateNotificationUseCase createNotificationCase;
    @Mock private DeleteNotificationUseCase deleteNotificationCase;
    @Mock private GetNotificationUseCase getNotificationCase;
    @Mock private ListAllNotificationsUseCase listNotificationsCase;
    @Mock private NotificationPublisher notificationPublisher;
    @Mock private GetUserUseCase getUserCase;
    @Mock private NotificationDtoMapper notificationMapper;

    @InjectMocks private NotificationService notificationService;

    private User buildUser(Long id) {
        return new User(id, "John", "+5511999999999", "john@example.com",
                LocalDateTime.now(), LocalDateTime.now());
    }

    private Notification buildNotification(Long id, User user) {
        return new Notification(id, "Hello!", NotificationChannel.EMAIL,
                NotificationStatus.PENDING, NotificationProvider.GMAIL, user, LocalDateTime.now(), LocalDateTime.now());
    }

    private NotificationResponse buildResponse(Long id) {
        return new NotificationResponse(id, "Hello!", NotificationChannel.EMAIL,
                NotificationStatus.SENT, NotificationProvider.GMAIL, 1L, LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void createNotification_shouldSetUserCreateSendAndReturnResponse() {
        var user = buildUser(1L);
        var request = new NotificationRequest("Hello!", NotificationChannel.EMAIL, NotificationStatus.PENDING, NotificationProvider.GMAIL, 1L);
        var entity = buildNotification(null, null);
        var saved = buildNotification(1L, user);
        var response = buildResponse(1L);

        when(notificationMapper.toEntity(request)).thenReturn(entity);
        when(getUserCase.execute(1L)).thenReturn(Optional.of(user));
        when(createNotificationCase.execute(entity)).thenReturn(saved);
        when(notificationMapper.toResponse(saved)).thenReturn(response);

        var result = notificationService.createNotification(request);

        assertEquals(response, result);
        assertEquals(user, entity.getUser());
        verify(notificationPublisher).publish(saved);
    }

    @Test
    void createNotification_whenUserNotFound_shouldThrowIllegalArgumentException() {
        var request = new NotificationRequest("Hello!", NotificationChannel.EMAIL, NotificationStatus.PENDING, NotificationProvider.GMAIL, 99L);
        var entity = buildNotification(null, null);

        when(notificationMapper.toEntity(request)).thenReturn(entity);
        when(getUserCase.execute(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> notificationService.createNotification(request));
        verify(createNotificationCase, never()).execute(any());
    }

    @Test
    void deleteNotification_shouldDelegateToUseCase() {
        notificationService.deleteNotification(1L);

        verify(deleteNotificationCase).execute(1L);
    }

    @Test
    void getNotificationById_whenFound_shouldReturnResponse() {
        var user = buildUser(1L);
        var notification = buildNotification(1L, user);
        var response = buildResponse(1L);

        when(getNotificationCase.execute(1L)).thenReturn(Optional.of(notification));
        when(notificationMapper.toResponse(notification)).thenReturn(response);

        var result = notificationService.getNotificationById(1L);

        assertTrue(result.isPresent());
        assertEquals(response, result.get());
    }

    @Test
    void getNotificationById_whenNotFound_shouldReturnEmpty() {
        when(getNotificationCase.execute(99L)).thenReturn(Optional.empty());

        var result = notificationService.getNotificationById(99L);

        assertTrue(result.isEmpty());
        verify(notificationMapper, never()).toResponse(any());
    }

    @Test
    void updateNotification_whenUserAndNotificationExist_shouldUpdate() {
        var user = buildUser(1L);
        var request = new NotificationRequest("Updated!", NotificationChannel.SMS, NotificationStatus.PENDING, NotificationProvider.GMAIL, 1L);
        var updatedEntity = buildNotification(null, null);
        var existing = buildNotification(1L, user);

        when(notificationMapper.toEntity(request)).thenReturn(updatedEntity);
        when(getUserCase.execute(1L)).thenReturn(Optional.of(user));
        when(getNotificationCase.execute(1L)).thenReturn(Optional.of(existing));

        notificationService.updateNotification(1L, request);

        assertEquals(1L, updatedEntity.getId());
        verify(createNotificationCase).execute(updatedEntity);
    }

    @Test
    void updateNotification_whenUserNotFound_shouldThrowException() {
        var request = new NotificationRequest("Updated!", NotificationChannel.SMS, NotificationStatus.PENDING, NotificationProvider.GMAIL, 99L);
        when(notificationMapper.toEntity(request)).thenReturn(buildNotification(null, null));
        when(getUserCase.execute(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> notificationService.updateNotification(1L, request));
        verify(createNotificationCase, never()).execute(any());
    }

    @Test
    void updateNotification_whenNotificationNotFound_shouldDoNothing() {
        var user = buildUser(1L);
        var request = new NotificationRequest("Updated!", NotificationChannel.SMS, NotificationStatus.PENDING, NotificationProvider.GMAIL, 1L);
        when(notificationMapper.toEntity(request)).thenReturn(buildNotification(null, null));
        when(getUserCase.execute(1L)).thenReturn(Optional.of(user));
        when(getNotificationCase.execute(1L)).thenReturn(Optional.empty());

        notificationService.updateNotification(1L, request);

        verify(createNotificationCase, never()).execute(any());
    }

    @Test
    void listAllNotifications_shouldReturnAllMappedResponses() {
        var user = buildUser(1L);
        var n1 = buildNotification(1L, user);
        var n2 = buildNotification(2L, user);
        var r1 = buildResponse(1L);
        var r2 = buildResponse(2L);

        when(listNotificationsCase.execute()).thenReturn(List.of(n1, n2));
        when(notificationMapper.toResponse(n1)).thenReturn(r1);
        when(notificationMapper.toResponse(n2)).thenReturn(r2);

        var result = notificationService.listAllNotifications();

        assertEquals(2, result.size());
    }
}
