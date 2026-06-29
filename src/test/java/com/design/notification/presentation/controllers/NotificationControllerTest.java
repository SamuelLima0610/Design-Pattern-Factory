package com.design.notification.presentation.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import tools.jackson.databind.ObjectMapper;
import com.design.notification.application.dtos.notification.NotificationRequest;
import com.design.notification.application.dtos.notification.NotificationResponse;
import com.design.notification.application.services.NotificationService;
import com.design.notification.domain.enums.NotificationChannel;
import com.design.notification.domain.enums.NotificationProvider;
import com.design.notification.domain.enums.NotificationStatus;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockitoBean private NotificationService notificationService;

    private NotificationResponse buildResponse(Long id) {
        return new NotificationResponse(id, "Hello!", NotificationChannel.EMAIL,
                NotificationStatus.SENT, NotificationProvider.GMAIL, 1L, LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void createNotification_shouldReturn200WithResponse() throws Exception {
        var request = new NotificationRequest("Hello!", NotificationChannel.EMAIL, NotificationStatus.PENDING, NotificationProvider.GMAIL, 1L);
        var response = buildResponse(1L);
        when(notificationService.createNotification(any())).thenReturn(response);

        mockMvc.perform(post("/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.message").value("Hello!"))
                .andExpect(jsonPath("$.channel").value("EMAIL"))
                .andExpect(jsonPath("$.status").value("SENT"));
    }

    @Test
    void getNotification_whenFound_shouldReturn200() throws Exception {
        var response = buildResponse(1L);
        when(notificationService.getNotificationById(1L)).thenReturn(Optional.of(response));

        mockMvc.perform(get("/notifications/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getNotification_whenNotFound_shouldReturn404() throws Exception {
        when(notificationService.getNotificationById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/notifications/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllNotifications_shouldReturn200WithList() throws Exception {
        var r1 = buildResponse(1L);
        var r2 = buildResponse(2L);
        when(notificationService.listAllNotifications()).thenReturn(List.of(r1, r2));

        mockMvc.perform(get("/notifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getAllNotifications_whenEmpty_shouldReturn200WithEmptyList() throws Exception {
        when(notificationService.listAllNotifications()).thenReturn(List.of());

        mockMvc.perform(get("/notifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void deleteNotification_shouldReturn204() throws Exception {
        doNothing().when(notificationService).deleteNotification(1L);

        mockMvc.perform(delete("/notifications/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateNotification_shouldReturn204() throws Exception {
        var request = new NotificationRequest("Updated!", NotificationChannel.SMS, NotificationStatus.PENDING, NotificationProvider.GMAIL, 1L);
        doNothing().when(notificationService).updateNotification(eq(1L), any());

        mockMvc.perform(put("/notifications/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    void createNotification_whenUserNotFound_shouldReturn500() throws Exception {
        var request = new NotificationRequest("Hello!", NotificationChannel.EMAIL, NotificationStatus.PENDING, NotificationProvider.GMAIL, 99L);
        when(notificationService.createNotification(any()))
                .thenThrow(new IllegalArgumentException("User not found: 99"));

        mockMvc.perform(post("/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("User not found: 99"));
    }
}
