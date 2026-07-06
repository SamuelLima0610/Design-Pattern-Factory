package com.design.notification.domain.entities;

public class Attachment {

    private String fileName;
    private String contentType;
    private String url;
    private Double fileSize;

    public Attachment() {
    }

    public Attachment(String fileName, String contentType, String url, Double fileSize) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.url = url;
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getFileSize() {
        return fileSize;
    }

    public void setFileSize(Double fileSize) {
        this.fileSize = fileSize;
    }
}
