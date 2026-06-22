package com.design.notification.infrastructure.repositories;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.design.notification.domain.entities.Notification;
import com.design.notification.domain.entities.User;
import com.design.notification.domain.enums.NotificationChannel;
import com.design.notification.domain.enums.NotificationStatus;
import com.design.notification.infrastructure.entities.NotificationEntity;
import com.design.notification.infrastructure.entities.UserEntity;
import com.design.notification.infrastructure.mappers.NotificationEntityMapper;

@ExtendWith(MockitoExtension.class)
class NotificationRepositoryImplTest {

    @Mock private NotificationJpaRepository jpaRepository;
    @Mock private NotificationEntityMapper mapper;

    @InjectMocks private NotificationRepositoryImpl repository;

    private User buildUser() {
        return new User(1L, "John", "+5511999", "j@j.com", LocalDateTime.now(), LocalDateTime.now());
    }

    private Notification buildNotification(Long id) {
        return new Notification(id, "Hello!", NotificationChannel.EMAIL,
                NotificationStatus.PENDING, buildUser(), LocalDateTime.now(), LocalDateTime.now());
    }

    private NotificationEntity buildEntity(Long id) {
        var userEntity = new UserEntity(1L, "John", "+5511999", "j@j.com", null, LocalDateTime.now(), LocalDateTime.now());
        return new NotificationEntity(id, "Hello!", NotificationChannel.EMAIL,
                NotificationStatus.PENDING, userEntity, LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void save_shouldMapThenPersistAndReturnDomain() {
        var domain = buildNotification(null);
        var entity = buildEntity(null);
        var savedEntity = buildEntity(1L);
        var savedDomain = buildNotification(1L);

        when(mapper.toEntity(domain)).thenReturn(entity);
        when(jpaRepository.save(entity)).thenReturn(savedEntity);
        when(mapper.toDomain(savedEntity)).thenReturn(savedDomain);

        var result = repository.save(domain);

        assertEquals(savedDomain, result);
        verify(jpaRepository).save(entity);
    }

    @Test
    void findById_whenFound_shouldReturnMappedDomain() {
        var entity = buildEntity(1L);
        var domain = buildNotification(1L);
        when(jpaRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        var result = repository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(domain, result.get());
    }

    @Test
    void findById_whenNotFound_shouldReturnEmpty() {
        when(jpaRepository.findById(99L)).thenReturn(Optional.empty());

        var result = repository.findById(99L);

        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_shouldReturnAllMappedDomains() {
        var entities = List.of(buildEntity(1L), buildEntity(2L));
        var domains = List.of(buildNotification(1L), buildNotification(2L));
        when(jpaRepository.findAll()).thenReturn(entities);
        when(mapper.toDomain(entities.get(0))).thenReturn(domains.get(0));
        when(mapper.toDomain(entities.get(1))).thenReturn(domains.get(1));

        var result = repository.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void deleteById_shouldDelegateToJpa() {
        repository.deleteById(1L);
        verify(jpaRepository).deleteById(1L);
    }

    @Test
    void existsById_shouldDelegateToJpa() {
        when(jpaRepository.existsById(1L)).thenReturn(true);
        when(jpaRepository.existsById(99L)).thenReturn(false);

        assertTrue(repository.existsById(1L));
        assertFalse(repository.existsById(99L));
    }
}
