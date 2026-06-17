package com.design.notification.domain.usecases.user;

import java.util.List;

import org.springframework.stereotype.Component;

import com.design.notification.domain.entities.User;
import com.design.notification.domain.repositories.UserRepository;

@Component
public class ListAllUsersUseCase {
    
    private final UserRepository userRepository;

    public ListAllUsersUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> execute() {
        return userRepository.findAll();
    }
}
