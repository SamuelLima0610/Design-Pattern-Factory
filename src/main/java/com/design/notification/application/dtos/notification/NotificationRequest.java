package com.design.notification.application.dtos.notification;

import java.util.List;

import com.design.notification.application.dtos.attachment.AttachmentRequest;
import com.design.notification.application.dtos.recipient.RecipientRequest;
import com.design.notification.domain.enums.NotificationChannel;
import com.design.notification.domain.enums.NotificationProvider;
import com.design.notification.domain.enums.NotificationStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

    private String title;
    private String subject;
    private String message;
    private NotificationChannel channel;
    private NotificationStatus status;
    private NotificationProvider provider;
    private Long userId;
    private List<RecipientRequest> recipients;
    private List<AttachmentRequest> attachments;
    
}
