package com.design.notification.infrastructure.mappers;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.design.notification.domain.entities.Notification;
import com.design.notification.domain.entities.User;
import com.design.notification.domain.enums.NotificationChannel;
import com.design.notification.domain.enums.NotificationProvider;
import com.design.notification.domain.enums.NotificationStatus;
import com.design.notification.infrastructure.entities.NotificationEntity;
import com.design.notification.infrastructure.entities.UserEntity;

class EntityMappersTest {

    private final UserEntityMapper userMapper = new UserEntityMapperImpl();
    private final NotificationEntityMapper notificationMapper = new NotificationEntityMapperImpl();

    private User buildUser(Long id) {
        return new User(id, "John", "+5511999", "j@j.com",
                LocalDateTime.of(2024, 1, 1, 10, 0),
                LocalDateTime.of(2024, 1, 2, 10, 0));
    }

    private UserEntity buildUserEntity(Long id) {
        return new UserEntity(id, "John", "+5511999", "j@j.com", null,
                LocalDateTime.of(2024, 1, 1, 10, 0),
                LocalDateTime.of(2024, 1, 2, 10, 0));
    }

    // ── UserEntityMapper ──────────────────────────────────────────────────────

    @Test
    void userMapper_toDomain_shouldMapAllFields() {
        var entity = buildUserEntity(1L);
        User domain = userMapper.toDomain(entity);

        assertEquals(1L, domain.getId());
        assertEquals("John", domain.getName());
        assertEquals("+5511999", domain.getPhoneNumber());
        assertEquals("j@j.com", domain.getEmail());
        assertEquals(entity.getCreatedAt(), domain.getCreatedAt());
        assertEquals(entity.getUpdatedAt(), domain.getUpdatedAt());
    }

    @Test
    void userMapper_toDomain_null_shouldReturnNull() {
        assertNull(userMapper.toDomain(null));
    }

    @Test
    void userMapper_toEntity_shouldMapAllFields() {
        var domain = buildUser(1L);
        UserEntity entity = userMapper.toEntity(domain);

        assertEquals(1L, entity.getId());
        assertEquals("John", entity.getName());
        assertEquals("+5511999", entity.getPhoneNumber());
        assertEquals("j@j.com", entity.getEmail());
        assertEquals(domain.getCreatedAt(), entity.getCreatedAt());
        assertEquals(domain.getUpdatedAt(), entity.getUpdatedAt());
    }

    @Test
    void userMapper_toEntity_null_shouldReturnNull() {
        assertNull(userMapper.toEntity(null));
    }

    // ── NotificationEntityMapper ──────────────────────────────────────────────

    @Test
    void notificationMapper_toDomain_shouldMapAllFieldsIncludingUser() {
        var userEntity = buildUserEntity(1L);
        var entity = new NotificationEntity(1L, "Hello!", "Subject", "Message", NotificationChannel.EMAIL,
                NotificationStatus.SENT, NotificationProvider.GMAIL, userEntity,
                new ArrayList<>(),
                new ArrayList<>(),
                LocalDateTime.of(2024, 1, 1, 10, 0),
                LocalDateTime.of(2024, 1, 2, 10, 0));

        Notification domain = notificationMapper.toDomain(entity);

        assertEquals(1L, domain.getId());
        assertEquals("Hello!", domain.getTitle());
        assertEquals("Subject", domain.getSubject());
        assertEquals("Message", domain.getMessage());
        assertEquals(NotificationChannel.EMAIL, domain.getChannel());
        assertEquals(NotificationStatus.SENT, domain.getStatus());
        assertEquals(NotificationProvider.GMAIL, domain.getProvider());
        assertNotNull(domain.getUser());
        assertEquals(1L, domain.getUser().getId());
        assertEquals(entity.getCreatedAt(), domain.getCreatedAt());
        assertEquals(entity.getUpdatedAt(), domain.getUpdatedAt());
    }

    @Test
    void notificationMapper_toDomain_nullUser_shouldReturnDomainWithNullUser() {
        var entity = new NotificationEntity(1L, "Hi", "Subject", "Message", NotificationChannel.SMS,
                NotificationStatus.PENDING, NotificationProvider.GMAIL, null,
                new ArrayList<>(), new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now());

        Notification domain = notificationMapper.toDomain(entity);

        assertNotNull(domain);
        assertNull(domain.getUser());
    }

    @Test
    void notificationMapper_toDomain_null_shouldReturnNull() {
        assertNull(notificationMapper.toDomain(null));
    }

    @Test
    void notificationMapper_toEntity_shouldMapAllFieldsIncludingUser() {
        var user = buildUser(1L);
        var domain = new Notification(1L, "Hello!", "Subject", "Message", NotificationChannel.WHATSAPP,
                NotificationStatus.PENDING, NotificationProvider.GMAIL, user,
                LocalDateTime.of(2024, 1, 1, 10, 0),
                LocalDateTime.of(2024, 1, 2, 10, 0));

        NotificationEntity entity = notificationMapper.toEntity(domain);

        assertEquals(1L, entity.getId());
        assertEquals("Hello!", entity.getTitle());
        assertEquals("Subject", entity.getSubject());
        assertEquals("Message", entity.getMessage());
        assertEquals(NotificationChannel.WHATSAPP, entity.getChannel());
        assertEquals(NotificationStatus.PENDING, entity.getStatus());
        assertEquals(NotificationProvider.GMAIL, entity.getProvider());
        assertNotNull(entity.getUser());
        assertEquals(1L, entity.getUser().getId());
    }

    @Test
    void notificationMapper_toEntity_nullUser_shouldReturnEntityWithNullUser() {
        var domain = new Notification(1L, "Hi", "Subject", "Message", NotificationChannel.PUSH,
                NotificationStatus.SENT, NotificationProvider.GMAIL, null,
                LocalDateTime.now(), LocalDateTime.now());

        NotificationEntity entity = notificationMapper.toEntity(domain);

        assertNotNull(entity);
        assertNull(entity.getUser());
    }

    @Test
    void notificationMapper_toEntity_null_shouldReturnNull() {
        assertNull(notificationMapper.toEntity(null));
    }
}
