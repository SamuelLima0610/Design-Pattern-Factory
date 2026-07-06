package com.design.notification.domain.builder;

import java.util.List;

import com.design.notification.application.dtos.attachment.AttachmentRequest;

public interface AttachmentBuilder {

    void setAttachments(List<AttachmentRequest> attachments);
}
