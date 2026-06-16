package com.design.notification.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.design.notification.infrastructure.entities.UserEntity;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    
}
