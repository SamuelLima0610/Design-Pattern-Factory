package com.design.notification.domain.usecases.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.design.notification.domain.entities.User;
import com.design.notification.domain.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserUseCasesTest {

    @Mock private UserRepository userRepository;

    private User buildUser(Long id) {
        return new User(id, "John", "+5511999999999", "john@example.com",
                LocalDateTime.now(), LocalDateTime.now());
    }

    // ── CreateUserUseCase ────────────────────────────────────────────────────

    @Test
    void createUser_shouldSaveUserAndReturn() {
        var useCase = new CreateUserUseCase(userRepository);
        var user = buildUser(null);
        var saved = buildUser(1L);
        when(userRepository.save(user)).thenReturn(saved);

        var result = useCase.execute(user);

        assertEquals(saved, result);
        verify(userRepository).save(user);
    }

    // ── GetUserUseCase ───────────────────────────────────────────────────────

    @Test
    void getUser_whenFound_shouldReturnOptionalWithUser() {
        var useCase = new GetUserUseCase(userRepository);
        var user = buildUser(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        var result = useCase.execute(1L);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void getUser_whenNotFound_shouldReturnEmpty() {
        var useCase = new GetUserUseCase(userRepository);
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        var result = useCase.execute(99L);

        assertTrue(result.isEmpty());
    }

    // ── DeleteUserUseCase ────────────────────────────────────────────────────

    @Test
    void deleteUser_shouldCallRepositoryDeleteById() {
        var useCase = new DeleteUserUseCase(userRepository);

        useCase.execute(1L);

        verify(userRepository).deleteById(1L);
    }

    // ── ListAllUsersUseCase ──────────────────────────────────────────────────

    @Test
    void listAllUsers_shouldReturnAllFromRepository() {
        var useCase = new ListAllUsersUseCase(userRepository);
        var users = List.of(buildUser(1L), buildUser(2L));
        when(userRepository.findAll()).thenReturn(users);

        var result = useCase.execute();

        assertEquals(2, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void listAllUsers_whenEmpty_shouldReturnEmptyList() {
        var useCase = new ListAllUsersUseCase(userRepository);
        when(userRepository.findAll()).thenReturn(List.of());

        var result = useCase.execute();

        assertTrue(result.isEmpty());
    }

    // ── UpdateUserUseCase ────────────────────────────────────────────────────

    @Test
    void updateUser_whenFound_shouldSetIdAndSave() {
        var useCase = new UpdateUserUseCase(userRepository);
        var existing = buildUser(1L);
        var updated = new User(null, "Jane", "+5511888888888", "jane@example.com", null, null);
        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));

        useCase.execute(1L, updated);

        assertEquals(1L, updated.getId());
        verify(userRepository).save(updated);
    }

    @Test
    void updateUser_whenNotFound_shouldNotSave() {
        var useCase = new UpdateUserUseCase(userRepository);
        var updated = buildUser(null);
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        useCase.execute(99L, updated);

        verify(userRepository, never()).save(any());
    }
}
