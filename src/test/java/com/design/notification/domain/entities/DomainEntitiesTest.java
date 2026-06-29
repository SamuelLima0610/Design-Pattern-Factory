package com.design.notification.domain.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.design.notification.domain.enums.NotificationChannel;
import com.design.notification.domain.enums.NotificationProvider;
import com.design.notification.domain.enums.NotificationStatus;

class DomainEntitiesTest {

    // ── User ─────────────────────────────────────────────────────────────────

    private User buildUser(Long id) {
        return new User(id, "John", "+5511999999999", "john@example.com",
                LocalDateTime.of(2024, 1, 1, 10, 0),
                LocalDateTime.of(2024, 1, 2, 10, 0));
    }

    @Test
    void user_noArgConstructor_shouldCreateEmpty() {
        var user = new User();
        assertNull(user.getId());
        assertNull(user.getName());
    }

    @Test
    void user_allArgsConstructor_shouldSetAllFields() {
        var now = LocalDateTime.now();
        var user = new User(1L, "John", "+5511999", "john@test.com", now, now);
        assertEquals(1L, user.getId());
        assertEquals("John", user.getName());
        assertEquals("+5511999", user.getPhoneNumber());
        assertEquals("john@test.com", user.getEmail());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());
    }

    @Test
    void user_setters_shouldUpdateFields() {
        var user = new User();
        var now = LocalDateTime.now();
        user.setId(5L);
        user.setName("Alice");
        user.setPhoneNumber("+5599");
        user.setEmail("alice@test.com");
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        assertEquals(5L, user.getId());
        assertEquals("Alice", user.getName());
        assertEquals("+5599", user.getPhoneNumber());
        assertEquals("alice@test.com", user.getEmail());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());
    }

    @Test
    void user_equals_sameId_shouldBeEqual() {
        assertEquals(buildUser(1L), buildUser(1L));
    }

    @Test
    void user_equals_differentId_shouldNotBeEqual() {
        assertNotEquals(buildUser(1L), buildUser(2L));
    }

    @Test
    void user_equals_sameObject_shouldBeEqual() {
        var user = buildUser(1L);
        assertEquals(user, user);
    }

    @Test
    void user_equals_null_shouldNotBeEqual() {
        assertNotEquals(null, buildUser(1L));
    }

    @Test
    void user_equals_differentType_shouldNotBeEqual() {
        assertNotEquals("not a user", buildUser(1L));
    }

    @Test
    void user_hashCode_sameId_shouldBeEqual() {
        assertEquals(buildUser(1L).hashCode(), buildUser(1L).hashCode());
    }

    @Test
    void user_toString_shouldContainRelevantFields() {
        var user = buildUser(1L);
        var str = user.toString();
        assertTrue(str.contains("1"));
        assertTrue(str.contains("John"));
    }

    // ── Notification ─────────────────────────────────────────────────────────

    private Notification buildNotification(Long id) {
        return new Notification(id, "Hello!", NotificationChannel.EMAIL,
                NotificationStatus.PENDING, NotificationProvider.GMAIL, buildUser(1L),
                LocalDateTime.of(2024, 1, 1, 10, 0),
                LocalDateTime.of(2024, 1, 2, 10, 0));
    }

    @Test
    void notification_noArgConstructor_shouldCreateEmpty() {
        var n = new Notification();
        assertNull(n.getId());
        assertNull(n.getMessage());
    }

    @Test
    void notification_allArgsConstructor_shouldSetAllFields() {
        var now = LocalDateTime.now();
        var user = buildUser(1L);
        var n = new Notification(1L, "Hi", NotificationChannel.SMS, NotificationStatus.SENT, NotificationProvider.GMAIL, user, now, now);
        assertEquals(1L, n.getId());
        assertEquals("Hi", n.getMessage());
        assertEquals(NotificationChannel.SMS, n.getChannel());
        assertEquals(NotificationStatus.SENT, n.getStatus());
        assertEquals(NotificationProvider.GMAIL, n.getProvider());
        assertEquals(user, n.getUser());
        assertEquals(now, n.getCreatedAt());
        assertEquals(now, n.getUpdatedAt());
    }

    @Test
    void notification_setters_shouldUpdateFields() {
        var n = new Notification();
        var now = LocalDateTime.now();
        var user = buildUser(1L);
        n.setId(3L);
        n.setMessage("Test");
        n.setChannel(NotificationChannel.PUSH);
        n.setStatus(NotificationStatus.FAILED);
        n.setUser(user);
        n.setCreatedAt(now);
        n.setUpdatedAt(now);

        assertEquals(3L, n.getId());
        assertEquals("Test", n.getMessage());
        assertEquals(NotificationChannel.PUSH, n.getChannel());
        assertEquals(NotificationStatus.FAILED, n.getStatus());
        assertEquals(user, n.getUser());
        assertEquals(now, n.getCreatedAt());
        assertEquals(now, n.getUpdatedAt());
    }

    @Test
    void notification_equals_sameId_shouldBeEqual() {
        assertEquals(buildNotification(1L), buildNotification(1L));
    }

    @Test
    void notification_equals_differentId_shouldNotBeEqual() {
        assertNotEquals(buildNotification(1L), buildNotification(2L));
    }

    @Test
    void notification_equals_sameObject_shouldBeEqual() {
        var n = buildNotification(1L);
        assertEquals(n, n);
    }

    @Test
    void notification_equals_null_shouldNotBeEqual() {
        assertNotEquals(null, buildNotification(1L));
    }

    @Test
    void notification_equals_differentType_shouldNotBeEqual() {
        assertNotEquals("not a notification", buildNotification(1L));
    }

    @Test
    void notification_hashCode_sameId_shouldMatch() {
        assertEquals(buildNotification(1L).hashCode(), buildNotification(1L).hashCode());
    }

    @Test
    void notification_toString_shouldContainRelevantFields() {
        var n = buildNotification(1L);
        var str = n.toString();
        assertTrue(str.contains("Hello!"));
        assertTrue(str.contains("EMAIL"));
    }
}
