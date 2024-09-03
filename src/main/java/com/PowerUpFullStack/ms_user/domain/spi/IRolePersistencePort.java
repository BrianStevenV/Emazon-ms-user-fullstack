package com.PowerUpFullStack.ms_user.domain.spi;

import com.PowerUpFullStack.ms_user.domain.models.Role;

public interface IRolePersistencePort {
    Role findRoleById(Long roleId);
}
