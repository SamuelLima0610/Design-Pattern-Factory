package com.design.notification.domain.usecases.notification;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.design.notification.domain.entities.Notification;
import com.design.notification.domain.entities.User;
import com.design.notification.domain.enums.NotificationChannel;
import com.design.notification.domain.enums.NotificationProvider;
import com.design.notification.domain.enums.NotificationStatus;
import com.design.notification.domain.gateways.NotificationAbstractFactory;
import com.design.notification.domain.gateways.NotificationSender;
import com.design.notification.domain.gateways.NotificationFactory;
import com.design.notification.domain.gateways.NotificationValidator;
import com.design.notification.domain.repositories.NotificationRepository;

@ExtendWith(MockitoExtension.class)
class NotificationUseCasesTest {

    @Mock private NotificationRepository notificationRepository;
    @Mock private NotificationFactory senderFactory;
    @Mock private NotificationAbstractFactory abstractFactory;
    @Mock private NotificationSender notificationSender;
    @Mock private NotificationValidator notificationValidator;

    private User buildUser() {
        return new User(1L, "John", "+5511999999999", "john@example.com",
                LocalDateTime.now(), LocalDateTime.now());
    }

    private Notification buildNotification(Long id) {
        return new Notification(id, "Title", "Subject", "Hello!", NotificationChannel.EMAIL,
                NotificationStatus.PENDING, NotificationProvider.GMAIL, buildUser(), LocalDateTime.now(), LocalDateTime.now());
    }

    // ── CreateNotificationUseCase ────────────────────────────────────────────

    @Test
    void createNotification_shouldSaveAndReturn() {
        var useCase = new CreateNotificationUseCase(notificationRepository);
        var notification = buildNotification(null);
        var saved = buildNotification(1L);
        when(notificationRepository.save(notification)).thenReturn(saved);

        var result = useCase.execute(notification);

        assertEquals(saved, result);
        verify(notificationRepository).save(notification);
    }

    // ── DeleteNotificationUseCase ────────────────────────────────────────────

    @Test
    void deleteNotification_shouldCallRepositoryDeleteById() {
        var useCase = new DeleteNotificationUseCase(notificationRepository);

        useCase.execute(1L);

        verify(notificationRepository).deleteById(1L);
    }

    // ── GetNotificationUseCase ───────────────────────────────────────────────

    @Test
    void getNotification_whenFound_shouldReturnOptional() {
        var useCase = new GetNotificationUseCase(notificationRepository);
        var notification = buildNotification(1L);
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));

        var result = useCase.execute(1L);

        assertTrue(result.isPresent());
        assertEquals(notification, result.get());
    }

    @Test
    void getNotification_whenNotFound_shouldReturnEmpty() {
        var useCase = new GetNotificationUseCase(notificationRepository);
        when(notificationRepository.findById(99L)).thenReturn(Optional.empty());

        var result = useCase.execute(99L);

        assertTrue(result.isEmpty());
    }

    // ── ListAllNotificationsUseCase ──────────────────────────────────────────

    @Test
    void listAllNotifications_shouldReturnAll() {
        var useCase = new ListAllNotificationsUseCase(notificationRepository);
        var notifications = List.of(buildNotification(1L), buildNotification(2L));
        when(notificationRepository.findAll()).thenReturn(notifications);

        var result = useCase.execute();

        assertEquals(2, result.size());
    }

    // ── SendNotificationUseCase ──────────────────────────────────────────────

    @Test
    void sendNotification_onSuccess_shouldSetStatusSentAndSave() {
        var useCase = new SendNotificationUseCase(senderFactory, notificationRepository);
        var notification = buildNotification(1L);
        when(senderFactory.getFactory(NotificationProvider.GMAIL)).thenReturn(abstractFactory);
        when(abstractFactory.createValidator()).thenReturn(notificationValidator);
        when(abstractFactory.createSender()).thenReturn(notificationSender);

        useCase.execute(notification);

        assertEquals(NotificationStatus.SENT, notification.getStatus());
        verify(notificationValidator).validate(notification);
        verify(notificationSender).send(notification);
        verify(notificationRepository).save(notification);
    }

    @Test
    void sendNotification_onSendFailure_shouldSetStatusFailedSaveAndRethrow() {
        var useCase = new SendNotificationUseCase(senderFactory, notificationRepository);
        var notification = buildNotification(1L);
        when(senderFactory.getFactory(NotificationProvider.GMAIL)).thenReturn(abstractFactory);
        when(abstractFactory.createValidator()).thenReturn(notificationValidator);
        when(abstractFactory.createSender()).thenReturn(notificationSender);
        doThrow(new RuntimeException("Send error")).when(notificationSender).send(notification);

        assertThrows(RuntimeException.class, () -> useCase.execute(notification));
        assertEquals(NotificationStatus.FAILED, notification.getStatus());
        verify(notificationRepository).save(notification);
    }

    @Test
    void sendNotification_onFactoryFailure_shouldSetStatusFailedSaveAndRethrow() {
        var useCase = new SendNotificationUseCase(senderFactory, notificationRepository);
        var notification = buildNotification(1L);
        when(senderFactory.getFactory(NotificationProvider.GMAIL))
                .thenThrow(new IllegalArgumentException("Unsupported provider"));

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(notification));
        assertEquals(NotificationStatus.FAILED, notification.getStatus());
        verify(notificationRepository).save(notification);
    }

    // ── UpdateNotificationUseCase ────────────────────────────────────────────

    @Test
    void updateNotification_whenFound_shouldSetIdAndSave() {
        var useCase = new UpdateNotificationUseCase(notificationRepository);
        var existing = buildNotification(1L);
        var updated = new Notification(null, "Updated!", "Updated Subject", "Updated Message", NotificationChannel.SMS,
                NotificationStatus.PENDING, NotificationProvider.GMAIL, buildUser(), null, null);
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(existing));

        useCase.execute(1L, updated);

        assertEquals(1L, updated.getId());
        verify(notificationRepository).save(updated);
    }

    @Test
    void updateNotification_whenNotFound_shouldNotSave() {
        var useCase = new UpdateNotificationUseCase(notificationRepository);
        var updated = new Notification(null, "Updated!", "Updated Subject", "Updated Message", NotificationChannel.SMS,
                NotificationStatus.PENDING, NotificationProvider.GMAIL, buildUser(), null, null);
        when(notificationRepository.findById(99L)).thenReturn(Optional.empty());

        useCase.execute(99L, updated);

        verify(notificationRepository, never()).save(any());
    }
}
