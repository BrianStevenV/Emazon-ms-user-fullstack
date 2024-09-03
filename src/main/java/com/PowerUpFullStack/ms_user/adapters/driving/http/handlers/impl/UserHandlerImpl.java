package com.PowerUpFullStack.ms_user.adapters.driving.http.handlers.impl;

import com.PowerUpFullStack.ms_user.adapters.driving.http.dtos.request.UserRequestDto;
import com.PowerUpFullStack.ms_user.adapters.driving.http.handlers.IUserHandler;
import com.PowerUpFullStack.ms_user.adapters.driving.http.mappers.IUserRequestMapper;
import com.PowerUpFullStack.ms_user.domain.api.IUserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserHandlerImpl implements IUserHandler {
    private final IUserRequestMapper userRequestMapper;
    private final IUserServicePort userServicePort;
    @Override
    public void createUser(UserRequestDto userRequestDto) {
        userServicePort.createUser(userRequestMapper.toUser(userRequestDto));
    }


}
