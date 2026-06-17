package com.design.notification.application.dtos.notification;

import com.design.notification.infrastructure.enums.NotificationChannel;
import com.design.notification.infrastructure.enums.NotificationStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

    private String message;
    private NotificationChannel channel;
    private NotificationStatus status;
    private Long userId;
    
}
