package com.design.notification.infrastructure.gateways.senders;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.design.notification.domain.entities.Notification;
import com.design.notification.domain.entities.User;
import com.design.notification.domain.enums.NotificationChannel;
import com.design.notification.domain.enums.NotificationProvider;
import com.design.notification.domain.enums.NotificationStatus;

class NotificationSendersTest {

    private User buildUser() {
        return new User(1L, "John", "+5511999999999", "john@example.com",
                LocalDateTime.now(), LocalDateTime.now());
    }

    private Notification buildNotification(NotificationChannel channel) {
        return new Notification(1L, "Title", "Subject", "Message", channel,
                NotificationStatus.PENDING, NotificationProvider.GMAIL, buildUser(), LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void emailSender_send_shouldNotThrow() {
        var sender = new EmailNotificationSender();
        assertDoesNotThrow(() -> sender.send(buildNotification(NotificationChannel.EMAIL)));
    }

    @Test
    void smsSender_send_shouldNotThrow() {
        var sender = new SmsNotificationSender();
        assertDoesNotThrow(() -> sender.send(buildNotification(NotificationChannel.SMS)));
    }

    @Test
    void pushSender_send_shouldNotThrow() {
        var sender = new PushNotificationSender();
        assertDoesNotThrow(() -> sender.send(buildNotification(NotificationChannel.PUSH)));
    }

    @Test
    void whatsAppSender_send_shouldNotThrow() {
        var sender = new WhatsAppNotificationSender();
        assertDoesNotThrow(() -> sender.send(buildNotification(NotificationChannel.WHATSAPP)));
    }
}
