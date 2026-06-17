package com.design.notification.domain.usecases.user;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.design.notification.domain.entities.User;
import com.design.notification.domain.repositories.UserRepository;

@Component
public class GetUserUseCase {
    
    private final UserRepository userRepository;

    public GetUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> execute(Long id) {
        return userRepository.findById(id);
    }

}
