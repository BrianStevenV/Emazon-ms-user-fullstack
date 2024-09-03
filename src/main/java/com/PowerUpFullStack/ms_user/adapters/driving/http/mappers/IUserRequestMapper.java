package com.PowerUpFullStack.ms_user.adapters.driving.http.mappers;

import com.PowerUpFullStack.ms_user.adapters.driving.http.dtos.request.UserRequestDto;
import com.PowerUpFullStack.ms_user.domain.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserRequestMapper {
    User toUser (UserRequestDto userRequestDto);
}
