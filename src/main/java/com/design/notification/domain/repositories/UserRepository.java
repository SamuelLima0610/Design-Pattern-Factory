package com.design.notification.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.design.notification.domain.entities.User;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    List<User> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}
