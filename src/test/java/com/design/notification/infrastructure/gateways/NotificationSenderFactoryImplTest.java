package com.design.notification.infrastructure.gateways;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.design.notification.domain.enums.NotificationChannel;
import com.design.notification.infrastructure.gateways.senders.EmailNotificationSender;
import com.design.notification.infrastructure.gateways.senders.PushNotificationSender;
import com.design.notification.infrastructure.gateways.senders.SmsNotificationSender;
import com.design.notification.infrastructure.gateways.senders.WhatsAppNotificationSender;

@ExtendWith(MockitoExtension.class)
class NotificationSenderFactoryImplTest {

    @Mock private EmailNotificationSender emailSender;
    @Mock private SmsNotificationSender smsSender;
    @Mock private PushNotificationSender pushSender;
    @Mock private WhatsAppNotificationSender whatsAppSender;

    private NotificationSenderFactoryImpl factory;

    @BeforeEach
    void setUp() {
        factory = new NotificationSenderFactoryImpl(emailSender, smsSender, pushSender, whatsAppSender);
    }

    @Test
    void getSender_email_shouldReturnEmailSender() {
        assertEquals(emailSender, factory.getSender(NotificationChannel.EMAIL));
    }

    @Test
    void getSender_sms_shouldReturnSmsSender() {
        assertEquals(smsSender, factory.getSender(NotificationChannel.SMS));
    }

    @Test
    void getSender_push_shouldReturnPushSender() {
        assertEquals(pushSender, factory.getSender(NotificationChannel.PUSH));
    }

    @Test
    void getSender_whatsapp_shouldReturnWhatsAppSender() {
        assertEquals(whatsAppSender, factory.getSender(NotificationChannel.WHATSAPP));
    }
}
