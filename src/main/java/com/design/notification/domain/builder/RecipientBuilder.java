package com.design.notification.domain.builder;

import java.util.List;

import com.design.notification.application.dtos.recipient.RecipientRequest;

public interface RecipientBuilder {

    void setRecipients(List<RecipientRequest> recipients);
}
