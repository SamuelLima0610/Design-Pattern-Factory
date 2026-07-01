package com.design.notification.domain.enums;

import static com.design.notification.domain.enums.NotificationChannel.EMAIL;
import static com.design.notification.domain.enums.NotificationChannel.SMS;
import static com.design.notification.domain.enums.NotificationChannel.PUSH;
import static com.design.notification.domain.enums.NotificationChannel.WHATSAPP;

public enum NotificationProvider {

    GMAIL(EMAIL),
    SENDGRID(EMAIL),
    SES(EMAIL),

    TWILIO(SMS),
    ZENVIA(SMS),

    FIREBASE(PUSH),
    ONESIGNAL(PUSH),

    WHATSAPP_API(WHATSAPP);

    private final NotificationChannel channel;

    NotificationProvider(NotificationChannel channel) {
        this.channel = channel;
    }

    public NotificationChannel getChannel() {
        return channel;
    }
}