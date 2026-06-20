package com.design.notification.domain.usecases.notification;

import org.springframework.stereotype.Service;

import com.design.notification.domain.entities.Notification;
import com.design.notification.domain.enums.NotificationStatus;
import com.design.notification.domain.gateways.NotificationSenderFactory;
import com.design.notification.domain.repositories.NotificationRepository;

@Service
public class SendNotificationUseCase {

    private final NotificationSenderFactory senderFactory;
    private final NotificationRepository notificationRepository;

    public SendNotificationUseCase(NotificationSenderFactory senderFactory, NotificationRepository notificationRepository) {
        this.senderFactory = senderFactory;
        this.notificationRepository = notificationRepository;
    }

    public void execute(Notification notification) {
        try {
            var sender = senderFactory.getSender(notification.getChannel());
            sender.send(notification);
            notification.setStatus(NotificationStatus.SENT);
        } catch (Exception e) {
            notification.setStatus(NotificationStatus.FAILED);
            // Log do erro poderia ser feito aqui ou relançado
            throw e; 
        } finally {
            notificationRepository.save(notification);
        }
    }
}
