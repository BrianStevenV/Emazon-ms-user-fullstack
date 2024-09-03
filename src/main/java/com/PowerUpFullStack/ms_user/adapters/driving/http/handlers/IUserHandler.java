package com.PowerUpFullStack.ms_user.adapters.driving.http.handlers;

import com.PowerUpFullStack.ms_user.adapters.driving.http.dtos.request.UserRequestDto;


public interface IUserHandler {
    void createUser(UserRequestDto userRequestDto);
}
