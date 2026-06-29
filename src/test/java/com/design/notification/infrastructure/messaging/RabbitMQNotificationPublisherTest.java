package com.design.notification.infrastructure.messaging;

import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.design.notification.domain.entities.Notification;
import com.design.notification.domain.entities.User;
import com.design.notification.domain.enums.NotificationChannel;
import com.design.notification.domain.enums.NotificationProvider;
import com.design.notification.domain.enums.NotificationStatus;
import com.design.notification.infrastructure.config.RabbitMQConfig;

@ExtendWith(MockitoExtension.class)
class RabbitMQNotificationPublisherTest {

    @Mock private RabbitTemplate rabbitTemplate;

    @InjectMocks private RabbitMQNotificationPublisher publisher;

    private Notification buildNotification(Long id) {
        var user = new User(1L, "John", "+5511999999999", "john@example.com",
                LocalDateTime.now(), LocalDateTime.now());
        return new Notification(id, "Hello!", NotificationChannel.EMAIL,
                NotificationStatus.PENDING, NotificationProvider.GMAIL, user, LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void publish_shouldSendNotificationIdToCorrectExchangeAndRoutingKey() {
        var notification = buildNotification(42L);

        publisher.publish(notification);

        verify(rabbitTemplate).convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                42L
        );
    }
}
