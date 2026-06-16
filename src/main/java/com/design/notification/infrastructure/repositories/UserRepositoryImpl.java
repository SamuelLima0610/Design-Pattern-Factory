package com.design.notification.infrastructure.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.design.notification.domain.entities.User;
import com.design.notification.domain.repositories.UserRepository;
import com.design.notification.infrastructure.mappers.UserEntityMapper;

@Component
public class UserRepositoryImpl implements UserRepository {
    
    private final UserJpaRepository userJpaRepository;
    private final UserEntityMapper userMapper;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository, UserEntityMapper userMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User save(User user) {
        var entity = userMapper.toEntity(user);
        var savedEntity = userJpaRepository.save(entity);
        return userMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findById(Long id) {
        var entity = userJpaRepository.findById(id);
        return entity.map(userMapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        var entities = userJpaRepository.findAll();
        return entities.stream().map(userMapper::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        userJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return userJpaRepository.existsById(id);
    }


}
