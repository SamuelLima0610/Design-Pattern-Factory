package com.design.notification.infrastructure.gateways;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.design.notification.domain.enums.NotificationProvider;
import com.design.notification.domain.gateways.NotificationAbstractFactory;
import com.design.notification.domain.gateways.NotificationFactory;
import com.design.notification.infrastructure.gateways.factories.FirebaseNotificationFactory;
import com.design.notification.infrastructure.gateways.factories.GmailNotificationFactory;
import com.design.notification.infrastructure.gateways.factories.OnesignalNotificationFactory;
import com.design.notification.infrastructure.gateways.factories.SendGridNotificationFactory;
import com.design.notification.infrastructure.gateways.factories.SesNotificationFactory;
import com.design.notification.infrastructure.gateways.factories.TwilioNotificationFactory;
import com.design.notification.infrastructure.gateways.factories.WhatsAppApiNotificationFactory;
import com.design.notification.infrastructure.gateways.factories.ZenviaNotificationFactory;

@Component
public class NotificationFactoryProducer implements NotificationFactory {

    private final Map<NotificationProvider, NotificationAbstractFactory> factories;

    public NotificationFactoryProducer(
            GmailNotificationFactory gmailFactory,
            SendGridNotificationFactory sendGridFactory,
            SesNotificationFactory sesFactory,
            TwilioNotificationFactory twilioFactory,
            ZenviaNotificationFactory zenviaFactory,
            FirebaseNotificationFactory firebaseFactory,
            OnesignalNotificationFactory onesignalFactory,
            WhatsAppApiNotificationFactory whatsAppApiFactory) {
        this.factories = Map.of(
            NotificationProvider.GMAIL, gmailFactory,
            NotificationProvider.SENDGRID, sendGridFactory,
            NotificationProvider.SES, sesFactory,
            NotificationProvider.TWILIO, twilioFactory,
            NotificationProvider.ZENVIA, zenviaFactory,
            NotificationProvider.FIREBASE, firebaseFactory,
            NotificationProvider.ONESIGNAL, onesignalFactory,
            NotificationProvider.WHATSAPP_API, whatsAppApiFactory
        );
    }

    public NotificationAbstractFactory getFactory(NotificationProvider provider) {
        NotificationAbstractFactory factory = factories.get(provider);
        if (factory == null) {
            throw new IllegalArgumentException("Provider não suportado: " + provider);
        }
        return factory;
    }
}
