package com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.repositories;

import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {
    @Query(value = "SELECT * FROM role WHERE name = :nameRole", nativeQuery = true)
    Optional<RoleEntity> findByName(@Param("nameRole") String nameRole);
}
