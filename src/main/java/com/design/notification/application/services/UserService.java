package com.design.notification.application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.design.notification.application.dtos.user.UserRequest;
import com.design.notification.application.dtos.user.UserResponse;
import com.design.notification.application.mappers.UserDtoMapper;
import com.design.notification.domain.usecases.user.CreateUserUseCase;
import com.design.notification.domain.usecases.user.DeleteUserUseCase;
import com.design.notification.domain.usecases.user.GetUserUseCase;
import com.design.notification.domain.usecases.user.ListAllUsersUseCase;
import com.design.notification.domain.usecases.user.UpdateUserUseCase;

@Service
public class UserService {

    private final CreateUserUseCase createUserCase;
    private final GetUserUseCase getUserCase;
    private final DeleteUserUseCase deleteUserCase;
    private final ListAllUsersUseCase listUsersCase;
    private final UpdateUserUseCase updateUserCase;
    private final UserDtoMapper userMapper;

    public UserService(CreateUserUseCase createUserCase, GetUserUseCase getUserCase, DeleteUserUseCase deleteUserCase, ListAllUsersUseCase listUsersCase, UpdateUserUseCase updateUserCase, UserDtoMapper userMapper) {
        this.createUserCase = createUserCase;
        this.getUserCase = getUserCase;
        this.deleteUserCase = deleteUserCase;
        this.listUsersCase = listUsersCase;
        this.updateUserCase = updateUserCase;
        this.userMapper = userMapper;
    }

    public UserResponse createUser(UserRequest request) {
        var user = userMapper.toEntity(request);
        user = createUserCase.execute(user);
        return userMapper.toResponse(user);
    }

    public Optional<UserResponse> getUserById(Long id) {
        return getUserCase.execute(id).map(userMapper::toResponse);
    }

    public void updateUser(Long id, UserRequest request) {
        var updatedUser = userMapper.toEntity(request);
        updateUserCase.execute(id, updatedUser);
    }

    public void deleteUser(Long id) {
        deleteUserCase.execute(id);
    }

    public List<UserResponse> listAllUsers() {
        var users = listUsersCase.execute();
        return users.stream().map(userMapper::toResponse).toList();
    }
    
}
