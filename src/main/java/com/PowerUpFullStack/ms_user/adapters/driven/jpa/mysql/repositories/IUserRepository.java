package com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.repositories;

import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String mail);
}
