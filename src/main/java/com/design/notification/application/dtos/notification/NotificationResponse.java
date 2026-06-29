package com.design.notification.application.dtos.notification;

import java.time.LocalDateTime;

import com.design.notification.domain.enums.NotificationChannel;
import com.design.notification.domain.enums.NotificationProvider;
import com.design.notification.domain.enums.NotificationStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    
    private Long id;
    private String message;
    private NotificationChannel channel;
    private NotificationStatus status;
    private NotificationProvider provider;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
