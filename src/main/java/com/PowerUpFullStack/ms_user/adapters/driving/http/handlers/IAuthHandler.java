package com.PowerUpFullStack.ms_user.adapters.driving.http.handlers;

import com.PowerUpFullStack.ms_user.adapters.driving.http.dtos.request.LoginRequestDto;
import com.PowerUpFullStack.ms_user.adapters.driving.http.dtos.response.JwtResponseDto;

import java.text.ParseException;

public interface IAuthHandler {
    JwtResponseDto login(LoginRequestDto loginRequestDto);
    JwtResponseDto refresh(JwtResponseDto jwtResponseDto) throws ParseException;
}
