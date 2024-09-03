package com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.mappers;

import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.entities.RoleEntity;
import com.PowerUpFullStack.ms_user.domain.models.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRoleEntityMapper {
    Role toRole(RoleEntity roleEntity);
    RoleEntity toRoleEntity(Role role);
}
