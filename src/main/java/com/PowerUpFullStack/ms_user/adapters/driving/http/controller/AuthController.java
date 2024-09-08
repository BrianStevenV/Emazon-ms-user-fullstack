package com.PowerUpFullStack.ms_user.adapters.driving.http.controller;

import com.PowerUpFullStack.ms_user.adapters.driving.http.dtos.request.LoginRequestDto;
import com.PowerUpFullStack.ms_user.adapters.driving.http.dtos.response.JwtResponseDto;
import com.PowerUpFullStack.ms_user.adapters.driving.http.handlers.IAuthHandler;
import com.PowerUpFullStack.ms_user.adapters.driving.http.utils.AuthControllerConstants;
import com.PowerUpFullStack.ms_user.configuration.OpenApiConfig.OpenApiConstants;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping(AuthControllerConstants.AUTH_CONTROLLER_REQUEST_MAPPING)
@RequiredArgsConstructor
public class AuthController {
    private final IAuthHandler authHandler;

    @PostMapping(AuthControllerConstants.AUTH_CONTROLLER_POST_LOGIN)
    public ResponseEntity<JwtResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return new ResponseEntity<>(authHandler.login(loginRequestDto), HttpStatus.OK);
    }

    @PostMapping(AuthControllerConstants.AUTH_CONTROLLER_POST_REFRESH)
    @SecurityRequirement(name = OpenApiConstants.SECURITY_REQUIREMENT)
    public ResponseEntity<JwtResponseDto> refresh(@RequestBody JwtResponseDto jwtResponseDto) throws ParseException {
        return new ResponseEntity<>(authHandler.refresh(jwtResponseDto), HttpStatus.OK);
    }
}
