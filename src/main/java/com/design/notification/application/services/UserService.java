package com.design.notification.application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.design.notification.application.dtos.UserRequest;
import com.design.notification.application.dtos.UserResponse;
import com.design.notification.application.mappers.UserDtoMapper;
import com.design.notification.domain.usecases.CreateUserUseCase;
import com.design.notification.domain.usecases.DeleteUserUseCase;
import com.design.notification.domain.usecases.GetUserUseCase;
import com.design.notification.domain.usecases.ListAllUsersUseCase;

@Service
public class UserService {

    private final CreateUserUseCase createUserCase;
    private final GetUserUseCase getUserCase;
    private final DeleteUserUseCase deleteUserCase;
    private final ListAllUsersUseCase listUsersCase;
    private final UserDtoMapper userMapper;

    public UserService(CreateUserUseCase createUserCase, GetUserUseCase getUserCase, DeleteUserUseCase deleteUserCase, ListAllUsersUseCase listUsersCase, UserDtoMapper userMapper) {
        this.createUserCase = createUserCase;
        this.getUserCase = getUserCase;
        this.deleteUserCase = deleteUserCase;
        this.listUsersCase = listUsersCase;
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

    public void deleteUser(Long id) {
        deleteUserCase.execute(id);
    }

    public List<UserResponse> listAllUsers() {
        var users = listUsersCase.execute();
        return users.stream().map(userMapper::toResponse).toList();
    }
    
}
