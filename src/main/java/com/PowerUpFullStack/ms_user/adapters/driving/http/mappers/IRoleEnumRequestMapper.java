package com.PowerUpFullStack.ms_user.adapters.driving.http.mappers;

import com.PowerUpFullStack.ms_user.adapters.driving.http.dtos.request.RoleEnumRequestDto;
import com.PowerUpFullStack.ms_user.domain.models.RoleEnum;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRoleEnumRequestMapper {
    RoleEnum toRoleEnum(RoleEnumRequestDto roleEnumRequestDto);
}
