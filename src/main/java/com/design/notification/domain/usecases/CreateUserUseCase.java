package com.design.notification.domain.usecases;

import org.springframework.stereotype.Component;

import com.design.notification.domain.entities.User;
import com.design.notification.domain.repositories.UserRepository;

@Component
public class CreateUserUseCase {
    
    private final UserRepository userRepository;

    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(User user){
        return userRepository.save(user);
    }
}
