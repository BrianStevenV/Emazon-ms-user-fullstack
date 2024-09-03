package com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.repositories;

import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {
}
