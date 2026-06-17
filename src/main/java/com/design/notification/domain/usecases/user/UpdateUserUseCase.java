package com.design.notification.domain.usecases.user;

import org.springframework.stereotype.Component;

import com.design.notification.domain.entities.User;
import com.design.notification.domain.repositories.UserRepository;

@Component
public class UpdateUserUseCase {
    
    private final UserRepository userRepository;

    public UpdateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(Long userId, User updatedUser) {
        var user = userRepository.findById(userId);
        if (user.isPresent()) {
            updatedUser.setId(user.get().getId());
            userRepository.save(updatedUser);
        }
    }
}
