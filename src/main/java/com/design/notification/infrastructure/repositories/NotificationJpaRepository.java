package com.design.notification.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.design.notification.infrastructure.entities.NotificationEntity;

@Repository
public interface NotificationJpaRepository extends JpaRepository<NotificationEntity, Long> {
    
}
