package com.design.notification.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.design.notification.domain.entities.Notification;

public interface NotificationRepository {
    Notification save(Notification notification);
    Optional<Notification> findById(Long id);
    List<Notification> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}
