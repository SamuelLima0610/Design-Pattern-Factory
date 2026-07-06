package com.design.notification.application.dtos.attachment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentRequest {

    private String fileName;
    private String contentType;
    private String url;
    private Double fileSize;
}
