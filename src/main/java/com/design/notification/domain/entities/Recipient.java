package com.design.notification.domain.entities;

import com.design.notification.domain.enums.NotificationStatus;

public class Recipient {

    private String address;
    private NotificationStatus status;
    private String errorMessage;

    public Recipient() {
    }

    public Recipient(String address, NotificationStatus status, String errorMessage) {
        this.address = address;
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
