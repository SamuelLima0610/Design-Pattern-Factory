package com.design.notification.infrastructure.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.design.notification.domain.entities.Notification;
import com.design.notification.domain.repositories.NotificationRepository;
import com.design.notification.infrastructure.entities.NotificationEntity;
import com.design.notification.infrastructure.mappers.NotificationEntityMapper;

@Component
public class NotificationRepositoryImpl implements NotificationRepository {

    private final NotificationJpaRepository jpaRepository;
    private final NotificationEntityMapper mapper;

    public NotificationRepositoryImpl(NotificationJpaRepository jpaRepository, NotificationEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Notification save(Notification notification) {
        NotificationEntity entity = mapper.toEntity(notification);
        if (entity.getAttachments() != null) {
            entity.getAttachments().forEach(attachment -> attachment.setNotification(entity));
        }
        if (entity.getRecipients() != null) {
            entity.getRecipients().forEach(recipient -> recipient.setNotification(entity));
        }
        NotificationEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Notification> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notification> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }
}
