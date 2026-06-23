package com.design.notification.infrastructure.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.design.notification.domain.usecases.notification.GetNotificationUseCase;
import com.design.notification.domain.usecases.notification.SendNotificationUseCase;
import com.design.notification.infrastructure.config.RabbitMQConfig;

@Component
public class NotificationConsumer {

    private static final Logger logger = LoggerFactory.getLogger(NotificationConsumer.class);

    private final GetNotificationUseCase getNotificationUseCase;
    private final SendNotificationUseCase sendNotificationUseCase;

    public NotificationConsumer(GetNotificationUseCase getNotificationUseCase,
            SendNotificationUseCase sendNotificationUseCase) {
        this.getNotificationUseCase = getNotificationUseCase;
        this.sendNotificationUseCase = sendNotificationUseCase;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void consume(Long notificationId) {
        logger.info("Mensagem recebida da fila: notificationId={}", notificationId);
        getNotificationUseCase.execute(notificationId).ifPresentOrElse(
            notification -> sendNotificationUseCase.execute(notification),
            () -> logger.warn("Notificação não encontrada para envio: id={}", notificationId)
        );
    }
}
