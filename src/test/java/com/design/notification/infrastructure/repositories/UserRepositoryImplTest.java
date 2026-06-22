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

import com.design.notification.domain.entities.User;
import com.design.notification.infrastructure.entities.UserEntity;
import com.design.notification.infrastructure.mappers.UserEntityMapper;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @Mock private UserJpaRepository userJpaRepository;
    @Mock private UserEntityMapper userMapper;

    @InjectMocks private UserRepositoryImpl repository;

    private User buildUser(Long id) {
        return new User(id, "John", "+5511999", "j@j.com", LocalDateTime.now(), LocalDateTime.now());
    }

    private UserEntity buildEntity(Long id) {
        return new UserEntity(id, "John", "+5511999", "j@j.com", null, LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void save_shouldMapPersistAndReturnDomain() {
        var domain = buildUser(null);
        var entity = buildEntity(null);
        var savedEntity = buildEntity(1L);
        var savedDomain = buildUser(1L);

        when(userMapper.toEntity(domain)).thenReturn(entity);
        when(userJpaRepository.save(entity)).thenReturn(savedEntity);
        when(userMapper.toDomain(savedEntity)).thenReturn(savedDomain);

        var result = repository.save(domain);

        assertEquals(savedDomain, result);
        verify(userJpaRepository).save(entity);
    }

    @Test
    void findById_whenFound_shouldReturnMappedDomain() {
        var entity = buildEntity(1L);
        var domain = buildUser(1L);
        when(userJpaRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(userMapper.toDomain(entity)).thenReturn(domain);

        var result = repository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(domain, result.get());
    }

    @Test
    void findById_whenNotFound_shouldReturnEmpty() {
        when(userJpaRepository.findById(99L)).thenReturn(Optional.empty());

        var result = repository.findById(99L);

        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_shouldReturnAllMappedDomains() {
        var entities = List.of(buildEntity(1L), buildEntity(2L));
        var domains = List.of(buildUser(1L), buildUser(2L));
        when(userJpaRepository.findAll()).thenReturn(entities);
        when(userMapper.toDomain(entities.get(0))).thenReturn(domains.get(0));
        when(userMapper.toDomain(entities.get(1))).thenReturn(domains.get(1));

        var result = repository.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void deleteById_shouldDelegateToJpa() {
        repository.deleteById(1L);
        verify(userJpaRepository).deleteById(1L);
    }

    @Test
    void existsById_shouldDelegateToJpa() {
        when(userJpaRepository.existsById(1L)).thenReturn(true);
        when(userJpaRepository.existsById(99L)).thenReturn(false);

        assertTrue(repository.existsById(1L));
        assertFalse(repository.existsById(99L));
    }
}
