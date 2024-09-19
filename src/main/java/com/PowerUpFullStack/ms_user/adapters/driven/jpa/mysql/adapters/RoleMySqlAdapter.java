package com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.adapters;

import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.mappers.IRoleEntityMapper;
import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.repositories.IRoleRepository;
import com.PowerUpFullStack.ms_user.domain.models.Role;
import com.PowerUpFullStack.ms_user.domain.spi.IRolePersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoleMySqlAdapter implements IRolePersistencePort {
    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;

    @Override
    public Role findRoleById(Long roleId) {
        return roleEntityMapper.toRole(roleRepository.findById(roleId).orElseThrow());
    }

    @Override
    public Role findRoleByName(String roleName) {
        return roleEntityMapper.toRole(roleRepository.findByName(roleName).orElseThrow());
    }
}
