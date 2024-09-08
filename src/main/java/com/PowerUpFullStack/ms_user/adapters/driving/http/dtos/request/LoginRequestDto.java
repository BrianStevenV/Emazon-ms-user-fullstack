package com.PowerUpFullStack.ms_user.adapters.driving.http.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
