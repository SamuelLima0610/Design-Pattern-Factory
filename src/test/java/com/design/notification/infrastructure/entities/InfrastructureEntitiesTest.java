package com.design.notification.infrastructure.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.design.notification.domain.enums.NotificationChannel;
import com.design.notification.domain.enums.NotificationProvider;
import com.design.notification.domain.enums.NotificationStatus;

class InfrastructureEntitiesTest {

    // ── UserEntity ────────────────────────────────────────────────────────────

    @Test
    void userEntity_noArgConstructor_shouldCreateInstance() {
        var entity = new UserEntity();
        assertNull(entity.getId());
    }

    @Test
    void userEntity_allArgsConstructor_shouldSetFields() {
        var now = LocalDateTime.now();
        var entity = new UserEntity(1L, "John", "+5511999", "john@test.com", null, now, now);
        assertEquals(1L, entity.getId());
        assertEquals("John", entity.getName());
        assertEquals("+5511999", entity.getPhoneNumber());
        assertEquals("john@test.com", entity.getEmail());
    }

    @Test
    void userEntity_setters_shouldUpdateAllFields() {
        var now = LocalDateTime.now();
        var entity = new UserEntity();
        entity.setId(2L);
        entity.setName("Alice");
        entity.setPhoneNumber("+5522222");
        entity.setEmail("alice@test.com");
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);

        assertEquals(2L, entity.getId());
        assertEquals("Alice", entity.getName());
        assertEquals("+5522222", entity.getPhoneNumber());
        assertEquals("alice@test.com", entity.getEmail());
        assertEquals(now, entity.getCreatedAt());
        assertEquals(now, entity.getUpdatedAt());
    }

    @Test
    void userEntity_onCreate_shouldSetTimestamps() {
        var entity = new UserEntity();
        entity.onCreate();
        assertNotNull(entity.getCreatedAt());
        assertNotNull(entity.getUpdatedAt());
    }

    @Test
    void userEntity_onUpdate_shouldRefreshUpdatedAt() throws InterruptedException {
        var entity = new UserEntity();
        entity.onCreate();
        var firstUpdate = entity.getUpdatedAt();
        Thread.sleep(10);
        entity.onUpdate();
        assertTrue(!entity.getUpdatedAt().isBefore(firstUpdate));
    }

    @Test
    void userEntity_equalsAndHashCode_sameFields_shouldBeEqual() {
        var time = LocalDateTime.of(2024, 1, 1, 10, 0);
        var e1 = new UserEntity(1L, "John", "+5511", "j@j.com", null, time, time);
        var e2 = new UserEntity(1L, "John", "+5511", "j@j.com", null, time, time);
        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
    }

    @Test
    void userEntity_toString_shouldNotThrow() {
        var entity = new UserEntity(1L, "John", "+5511", "j@j.com", null, LocalDateTime.now(), LocalDateTime.now());
        assertNotNull(entity.toString());
    }

    // ── NotificationEntity ────────────────────────────────────────────────────

    @Test
    void notificationEntity_noArgConstructor_shouldCreateInstance() {
        var entity = new NotificationEntity();
        assertNull(entity.getId());
    }

    @Test
    void notificationEntity_allArgsConstructor_shouldSetFields() {
        var now = LocalDateTime.now();
        var userEntity = new UserEntity(1L, "John", "+5511", "j@j.com", null, now, now);
        var entity = new NotificationEntity(1L, "Hi!", NotificationChannel.EMAIL,
                NotificationStatus.SENT, NotificationProvider.GMAIL, userEntity, now, now);
        assertEquals(1L, entity.getId());
        assertEquals("Hi!", entity.getMessage());
        assertEquals(NotificationChannel.EMAIL, entity.getChannel());
        assertEquals(NotificationStatus.SENT, entity.getStatus());
        assertEquals(NotificationProvider.GMAIL, entity.getProvider());
        assertEquals(userEntity, entity.getUser());
    }

    @Test
    void notificationEntity_setters_shouldUpdateFields() {
        var now = LocalDateTime.now();
        var entity = new NotificationEntity();
        entity.setId(5L);
        entity.setMessage("Test");
        entity.setChannel(NotificationChannel.SMS);
        entity.setStatus(NotificationStatus.PENDING);
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);

        assertEquals(5L, entity.getId());
        assertEquals("Test", entity.getMessage());
        assertEquals(NotificationChannel.SMS, entity.getChannel());
        assertEquals(NotificationStatus.PENDING, entity.getStatus());
    }

    @Test
    void notificationEntity_onCreate_shouldSetTimestamps() {
        var entity = new NotificationEntity();
        entity.onCreate();
        assertNotNull(entity.getCreatedAt());
        assertNotNull(entity.getUpdatedAt());
    }

    @Test
    void notificationEntity_onUpdate_shouldRefreshUpdatedAt() throws InterruptedException {
        var entity = new NotificationEntity();
        entity.onCreate();
        var firstUpdate = entity.getUpdatedAt();
        Thread.sleep(10);
        entity.onUpdate();
        assertTrue(!entity.getUpdatedAt().isBefore(firstUpdate));
    }

    @Test
    void notificationEntity_equalsAndHashCode_sameFields_shouldBeEqual() {
        var time = LocalDateTime.of(2024, 1, 1, 10, 0);
        var e1 = new NotificationEntity(1L, "A", NotificationChannel.EMAIL, NotificationStatus.SENT, NotificationProvider.GMAIL, null, time, time);
        var e2 = new NotificationEntity(1L, "A", NotificationChannel.EMAIL, NotificationStatus.SENT, NotificationProvider.GMAIL, null, time, time);
        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
    }

    @Test
    void notificationEntity_toString_shouldNotThrow() {
        var entity = new NotificationEntity(1L, "Hi", NotificationChannel.EMAIL,
                NotificationStatus.SENT, NotificationProvider.GMAIL, null, LocalDateTime.now(), LocalDateTime.now());
        assertNotNull(entity.toString());
    }
}
