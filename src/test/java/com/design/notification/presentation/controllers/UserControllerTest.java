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
import com.design.notification.application.dtos.user.UserRequest;
import com.design.notification.application.dtos.user.UserResponse;
import com.design.notification.application.services.UserService;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockitoBean private UserService userService;

    private UserResponse buildResponse(Long id) {
        return new UserResponse(id, "John", "+5511999999999", "john@example.com",
                LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void createUser_shouldReturn200WithCreatedUser() throws Exception {
        var request = new UserRequest("John", "+5511999999999", "john@example.com");
        var response = buildResponse(1L);
        when(userService.createUser(any())).thenReturn(response);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void getUser_whenFound_shouldReturn200() throws Exception {
        var response = buildResponse(1L);
        when(userService.getUserById(1L)).thenReturn(Optional.of(response));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void getUser_whenNotFound_shouldReturn404() throws Exception {
        when(userService.getUserById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllUsers_shouldReturn200WithList() throws Exception {
        var response = buildResponse(1L);
        when(userService.listAllUsers()).thenReturn(List.of(response));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void getAllUsers_whenEmpty_shouldReturn200WithEmptyList() throws Exception {
        when(userService.listAllUsers()).thenReturn(List.of());

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void updateUser_shouldReturn204() throws Exception {
        var request = new UserRequest("Jane", "+5511888888888", "jane@example.com");
        doNothing().when(userService).updateUser(eq(1L), any());

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUser_shouldReturn204() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createUser_whenServiceThrows_shouldReturn500() throws Exception {
        var request = new UserRequest("John", "+5511999999999", "john@example.com");
        when(userService.createUser(any())).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Unexpected error"));
    }
}
