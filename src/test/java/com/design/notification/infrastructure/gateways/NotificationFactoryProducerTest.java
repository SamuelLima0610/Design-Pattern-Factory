package com.design.notification.infrastructure.gateways;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.design.notification.domain.enums.NotificationProvider;
import com.design.notification.infrastructure.gateways.factories.FirebaseNotificationFactory;
import com.design.notification.infrastructure.gateways.factories.GmailNotificationFactory;
import com.design.notification.infrastructure.gateways.factories.OnesignalNotificationFactory;
import com.design.notification.infrastructure.gateways.factories.SendGridNotificationFactory;
import com.design.notification.infrastructure.gateways.factories.SesNotificationFactory;
import com.design.notification.infrastructure.gateways.factories.TwilioNotificationFactory;
import com.design.notification.infrastructure.gateways.factories.WhatsAppApiNotificationFactory;
import com.design.notification.infrastructure.gateways.factories.ZenviaNotificationFactory;

@ExtendWith(MockitoExtension.class)
class NotificationFactoryProducerTest {

    @Mock private GmailNotificationFactory gmailFactory;
    @Mock private SendGridNotificationFactory sendGridFactory;
    @Mock private SesNotificationFactory sesFactory;
    @Mock private TwilioNotificationFactory twilioFactory;
    @Mock private ZenviaNotificationFactory zenviaFactory;
    @Mock private FirebaseNotificationFactory firebaseFactory;
    @Mock private OnesignalNotificationFactory onesignalFactory;
    @Mock private WhatsAppApiNotificationFactory whatsAppApiFactory;

    private NotificationFactoryProducer producer;

    @BeforeEach
    void setUp() {
        producer = new NotificationFactoryProducer(
                gmailFactory, sendGridFactory, sesFactory,
                twilioFactory, zenviaFactory, firebaseFactory,
                onesignalFactory, whatsAppApiFactory);
    }

    @Test
    void getFactory_gmail_shouldReturnGmailFactory() {
        assertEquals(gmailFactory, producer.getFactory(NotificationProvider.GMAIL));
    }

    @Test
    void getFactory_sendGrid_shouldReturnSendGridFactory() {
        assertEquals(sendGridFactory, producer.getFactory(NotificationProvider.SENDGRID));
    }

    @Test
    void getFactory_ses_shouldReturnSesFactory() {
        assertEquals(sesFactory, producer.getFactory(NotificationProvider.SES));
    }

    @Test
    void getFactory_twilio_shouldReturnTwilioFactory() {
        assertEquals(twilioFactory, producer.getFactory(NotificationProvider.TWILIO));
    }

    @Test
    void getFactory_zenvia_shouldReturnZenviaFactory() {
        assertEquals(zenviaFactory, producer.getFactory(NotificationProvider.ZENVIA));
    }

    @Test
    void getFactory_firebase_shouldReturnFirebaseFactory() {
        assertEquals(firebaseFactory, producer.getFactory(NotificationProvider.FIREBASE));
    }

    @Test
    void getFactory_onesignal_shouldReturnOnesignalFactory() {
        assertEquals(onesignalFactory, producer.getFactory(NotificationProvider.ONESIGNAL));
    }

    @Test
    void getFactory_whatsappApi_shouldReturnWhatsAppApiFactory() {
        assertEquals(whatsAppApiFactory, producer.getFactory(NotificationProvider.WHATSAPP_API));
    }

}
