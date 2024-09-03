package com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.mappers;

import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.entities.UserEntity;
import com.PowerUpFullStack.ms_user.domain.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserEntityMapper {
    UserEntity toUserEntity(User user);
}
