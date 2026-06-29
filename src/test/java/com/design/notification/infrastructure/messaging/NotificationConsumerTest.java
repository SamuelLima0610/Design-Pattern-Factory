package com.design.notification.infrastructure.messaging;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.design.notification.domain.entities.Notification;
import com.design.notification.domain.entities.User;
import com.design.notification.domain.enums.NotificationChannel;
import com.design.notification.domain.enums.NotificationProvider;
import com.design.notification.domain.enums.NotificationStatus;
import com.design.notification.domain.usecases.notification.GetNotificationUseCase;
import com.design.notification.domain.usecases.notification.SendNotificationUseCase;

@ExtendWith(MockitoExtension.class)
class NotificationConsumerTest {

    @Mock private GetNotificationUseCase getNotificationUseCase;
    @Mock private SendNotificationUseCase sendNotificationUseCase;

    @InjectMocks private NotificationConsumer consumer;

    private Notification buildNotification(Long id) {
        var user = new User(1L, "John", "+5511999999999", "john@example.com",
                LocalDateTime.now(), LocalDateTime.now());
        return new Notification(id, "Hello!", NotificationChannel.EMAIL,
                NotificationStatus.PENDING, NotificationProvider.GMAIL, user, LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void consume_whenNotificationFound_shouldCallSendUseCase() {
        var notification = buildNotification(1L);
        when(getNotificationUseCase.execute(1L)).thenReturn(Optional.of(notification));

        consumer.consume(1L);

        verify(sendNotificationUseCase).execute(notification);
    }

    @Test
    void consume_whenNotificationNotFound_shouldNotCallSendUseCase() {
        when(getNotificationUseCase.execute(99L)).thenReturn(Optional.empty());

        consumer.consume(99L);

        verify(sendNotificationUseCase, never()).execute(any());
    }

    private static <T> T any() {
        return org.mockito.ArgumentMatchers.any();
    }
}
