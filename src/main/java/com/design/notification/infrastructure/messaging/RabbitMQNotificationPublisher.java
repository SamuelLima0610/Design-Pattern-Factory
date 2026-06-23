package com.design.notification.infrastructure.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.design.notification.domain.entities.Notification;
import com.design.notification.domain.gateways.NotificationPublisher;
import com.design.notification.infrastructure.config.RabbitMQConfig;

@Component
public class RabbitMQNotificationPublisher implements NotificationPublisher {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQNotificationPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(Notification notification) {
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE,
            RabbitMQConfig.ROUTING_KEY,
            notification.getId()
        );
    }
}
