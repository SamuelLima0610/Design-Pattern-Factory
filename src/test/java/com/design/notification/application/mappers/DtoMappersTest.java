package com.design.notification.application.mappers;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.design.notification.application.dtos.notification.NotificationRequest;
import com.design.notification.application.dtos.notification.NotificationResponse;
import com.design.notification.application.dtos.user.UserRequest;
import com.design.notification.application.dtos.user.UserResponse;
import com.design.notification.domain.entities.Notification;
import com.design.notification.domain.entities.User;
import com.design.notification.domain.enums.NotificationChannel;
import com.design.notification.domain.enums.NotificationProvider;
import com.design.notification.domain.enums.NotificationStatus;

class DtoMappersTest {

    private final UserDtoMapper userMapper = new UserDtoMapperImpl();
    private final NotificationDtoMapper notificationMapper = new NotificationDtoMapperImpl();

    private User buildUser(Long id) {
        return new User(id, "John", "+5511999999999", "john@example.com",
                LocalDateTime.of(2024, 1, 1, 10, 0),
                LocalDateTime.of(2024, 1, 2, 10, 0));
    }

    // ── UserDtoMapper ─────────────────────────────────────────────────────────

    @Test
    void userMapper_toResponse_shouldMapAllFields() {
        var user = buildUser(1L);
        UserResponse response = userMapper.toResponse(user);

        assertEquals(1L, response.getId());
        assertEquals("John", response.getName());
        assertEquals("+5511999999999", response.getPhoneNumber());
        assertEquals("john@example.com", response.getEmail());
        assertEquals(user.getCreatedAt(), response.getCreatedAt());
        assertEquals(user.getUpdatedAt(), response.getUpdatedAt());
    }

    @Test
    void userMapper_toResponse_null_shouldReturnNull() {
        assertNull(userMapper.toResponse(null));
    }

    @Test
    void userMapper_toEntity_shouldMapNamePhoneEmail() {
        var request = new UserRequest("John", "+5511999999999", "john@example.com");
        User user = userMapper.toEntity(request);

        assertEquals("John", user.getName());
        assertEquals("+5511999999999", user.getPhoneNumber());
        assertEquals("john@example.com", user.getEmail());
        assertNull(user.getId());
        assertNull(user.getCreatedAt());
        assertNull(user.getUpdatedAt());
    }

    @Test
    void userMapper_toEntity_null_shouldReturnNull() {
        assertNull(userMapper.toEntity(null));
    }

    // ── NotificationDtoMapper ─────────────────────────────────────────────────

    @Test
    void notificationMapper_toResponse_shouldMapAllFields() {
        var user = buildUser(1L);
        var notification = new Notification(1L, "Title", "Subject", "Hello!", NotificationChannel.EMAIL,
                NotificationStatus.SENT, NotificationProvider.GMAIL, user,
                LocalDateTime.of(2024, 1, 1, 10, 0),
                LocalDateTime.of(2024, 1, 2, 10, 0));

        NotificationResponse response = notificationMapper.toResponse(notification);

        assertEquals(1L, response.getId());
        assertEquals("Title", response.getTitle());
        assertEquals("Subject", response.getSubject());
        assertEquals("Hello!", response.getMessage());
        assertEquals(NotificationChannel.EMAIL, response.getChannel());
        assertEquals(NotificationStatus.SENT, response.getStatus());
        assertEquals(notification.getCreatedAt(), response.getCreatedAt());
        assertEquals(notification.getUpdatedAt(), response.getUpdatedAt());
    }

    @Test
    void notificationMapper_toResponse_null_shouldReturnNull() {
        assertNull(notificationMapper.toResponse(null));
    }

    @Test
    void notificationMapper_toEntity_shouldMapMessageChannelStatus() {
        var request = new NotificationRequest(
                "Title", "Subject", "Hello!", NotificationChannel.SMS, NotificationStatus.PENDING, NotificationProvider.GMAIL, 1L, null, null);

        Notification notification = notificationMapper.toEntity(request);

        assertEquals("Hello!", notification.getMessage());
        assertEquals(NotificationChannel.SMS, notification.getChannel());
        assertEquals(NotificationStatus.PENDING, notification.getStatus());
        assertEquals(NotificationProvider.GMAIL, notification.getProvider());
        assertNull(notification.getId());
        assertNull(notification.getUser());
        assertNull(notification.getCreatedAt());
        assertNull(notification.getUpdatedAt());
    }

    @Test
    void notificationMapper_toEntity_null_shouldReturnNull() {
        assertNull(notificationMapper.toEntity(null));
    }
}
