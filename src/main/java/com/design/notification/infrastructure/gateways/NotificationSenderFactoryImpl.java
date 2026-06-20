package com.design.notification.infrastructure.gateways;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.design.notification.domain.enums.NotificationChannel;
import com.design.notification.domain.gateways.NotificationSender;
import com.design.notification.domain.gateways.NotificationSenderFactory;
import com.design.notification.infrastructure.gateways.senders.EmailNotificationSender;
import com.design.notification.infrastructure.gateways.senders.PushNotificationSender;
import com.design.notification.infrastructure.gateways.senders.SmsNotificationSender;
import com.design.notification.infrastructure.gateways.senders.WhatsAppNotificationSender;

@Component
public class NotificationSenderFactoryImpl implements NotificationSenderFactory {

    private final Map<NotificationChannel, NotificationSender> senders;

    public NotificationSenderFactoryImpl(
            EmailNotificationSender emailSender,
            SmsNotificationSender smsSender,
            PushNotificationSender pushSender,
            WhatsAppNotificationSender whatsAppSender) {
        this.senders = Map.of(
            NotificationChannel.EMAIL, emailSender,
            NotificationChannel.SMS, smsSender,
            NotificationChannel.PUSH, pushSender,
            NotificationChannel.WHATSAPP, whatsAppSender
        );
    }

    @Override
    public NotificationSender getSender(NotificationChannel channel) {
        NotificationSender sender = senders.get(channel);
        if (sender == null) {
            throw new IllegalArgumentException("Canal de notificação não suportado: " + channel);
        }
        return sender;
    }
}
