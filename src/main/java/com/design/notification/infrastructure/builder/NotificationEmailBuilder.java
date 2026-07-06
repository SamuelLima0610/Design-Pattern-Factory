package com.design.notification.infrastructure.builder;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.design.notification.application.dtos.attachment.AttachmentRequest;
import com.design.notification.application.dtos.notification.NotificationRequest;
import com.design.notification.application.dtos.recipient.RecipientRequest;
import com.design.notification.domain.builder.AttachmentBuilder;
import com.design.notification.domain.builder.NotificationBuilder;
import com.design.notification.domain.builder.RecipientBuilder;
import com.design.notification.domain.entities.Attachment;
import com.design.notification.domain.entities.Notification;
import com.design.notification.domain.entities.Recipient;
import com.design.notification.domain.enums.NotificationStatus;

@Component
public class NotificationEmailBuilder implements NotificationBuilder, RecipientBuilder, AttachmentBuilder {

    private Notification notification;

    @Override
    public void setNotification(NotificationRequest request) {
        this.notification = new Notification();
        this.notification.setTitle(request.getTitle());
        this.notification.setSubject(request.getSubject());
        this.notification.setMessage(request.getMessage());
        this.notification.setChannel(request.getChannel());
        this.notification.setStatus(request.getStatus());
        this.notification.setProvider(request.getProvider());
    }

    @Override
    public void setRecipients(List<RecipientRequest> recipients) {
        List<Recipient> recipientList = recipients.stream()
                .map(r -> new Recipient(r.getAddress(), NotificationStatus.PENDING, null))
                .collect(Collectors.toList());
        this.notification.setRecipients(recipientList);
    }

    @Override
    public void setAttachments(List<AttachmentRequest> attachments) {
        List<Attachment> attachmentList = attachments.stream()
                .map(a -> new Attachment(a.getFileName(), a.getContentType(), a.getUrl(), a.getFileSize()))
                .collect(Collectors.toList());
        this.notification.setAttachments(attachmentList);
    }

    @Override
    public Notification build() {
        return this.notification;
    }
}

