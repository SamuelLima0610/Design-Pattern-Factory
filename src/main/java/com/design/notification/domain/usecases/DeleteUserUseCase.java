package com.design.notification.domain.usecases;

import org.springframework.stereotype.Component;

import com.design.notification.domain.repositories.UserRepository;

@Component
public class DeleteUserUseCase {
    
    private final UserRepository userRepository;

    public DeleteUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }  

    public void execute(Long id){
        userRepository.deleteById(id);
    }
}
