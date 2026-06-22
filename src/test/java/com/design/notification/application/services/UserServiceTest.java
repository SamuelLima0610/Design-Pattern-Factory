package com.design.notification.application.services;

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

import com.design.notification.application.dtos.user.UserRequest;
import com.design.notification.application.dtos.user.UserResponse;
import com.design.notification.application.mappers.UserDtoMapper;
import com.design.notification.domain.entities.User;
import com.design.notification.domain.usecases.user.CreateUserUseCase;
import com.design.notification.domain.usecases.user.DeleteUserUseCase;
import com.design.notification.domain.usecases.user.GetUserUseCase;
import com.design.notification.domain.usecases.user.ListAllUsersUseCase;
import com.design.notification.domain.usecases.user.UpdateUserUseCase;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private CreateUserUseCase createUserCase;
    @Mock private GetUserUseCase getUserCase;
    @Mock private DeleteUserUseCase deleteUserCase;
    @Mock private ListAllUsersUseCase listUsersCase;
    @Mock private UpdateUserUseCase updateUserCase;
    @Mock private UserDtoMapper userMapper;

    @InjectMocks private UserService userService;

    private User buildUser(Long id) {
        return new User(id, "John", "+5511999999999", "john@example.com",
                LocalDateTime.now(), LocalDateTime.now());
    }

    private UserResponse buildUserResponse(Long id) {
        return new UserResponse(id, "John", "+5511999999999", "john@example.com",
                LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void createUser_shouldMapRequestSaveAndReturnResponse() {
        var request = new UserRequest("John", "+5511999999999", "john@example.com");
        var user = buildUser(null);
        var savedUser = buildUser(1L);
        var response = buildUserResponse(1L);

        when(userMapper.toEntity(request)).thenReturn(user);
        when(createUserCase.execute(user)).thenReturn(savedUser);
        when(userMapper.toResponse(savedUser)).thenReturn(response);

        var result = userService.createUser(request);

        assertEquals(response, result);
        verify(createUserCase).execute(user);
        verify(userMapper).toResponse(savedUser);
    }

    @Test
    void getUserById_whenFound_shouldReturnResponse() {
        var user = buildUser(1L);
        var response = buildUserResponse(1L);
        when(getUserCase.execute(1L)).thenReturn(Optional.of(user));
        when(userMapper.toResponse(user)).thenReturn(response);

        var result = userService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals(response, result.get());
    }

    @Test
    void getUserById_whenNotFound_shouldReturnEmpty() {
        when(getUserCase.execute(99L)).thenReturn(Optional.empty());

        var result = userService.getUserById(99L);

        assertTrue(result.isEmpty());
        verify(userMapper, never()).toResponse(any());
    }

    @Test
    void updateUser_shouldMapAndDelegateToUseCase() {
        var request = new UserRequest("Jane", "+5511888888888", "jane@example.com");
        var updatedUser = buildUser(null);
        when(userMapper.toEntity(request)).thenReturn(updatedUser);

        userService.updateUser(1L, request);

        verify(updateUserCase).execute(1L, updatedUser);
    }

    @Test
    void deleteUser_shouldDelegateToUseCase() {
        userService.deleteUser(1L);

        verify(deleteUserCase).execute(1L);
    }

    @Test
    void listAllUsers_shouldReturnMappedResponses() {
        var user1 = buildUser(1L);
        var user2 = buildUser(2L);
        var response1 = buildUserResponse(1L);
        var response2 = buildUserResponse(2L);

        when(listUsersCase.execute()).thenReturn(List.of(user1, user2));
        when(userMapper.toResponse(user1)).thenReturn(response1);
        when(userMapper.toResponse(user2)).thenReturn(response2);

        var result = userService.listAllUsers();

        assertEquals(2, result.size());
        assertTrue(result.containsAll(List.of(response1, response2)));
    }

    @Test
    void listAllUsers_whenEmpty_shouldReturnEmptyList() {
        when(listUsersCase.execute()).thenReturn(List.of());

        var result = userService.listAllUsers();

        assertTrue(result.isEmpty());
    }
}
