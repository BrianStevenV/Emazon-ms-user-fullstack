package com.PowerUpFullStack.ms_user.adapters.driving.http.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record UserRequestDto(
        @NotBlank
        String name,
        @NotBlank
        String surName,
        @NotBlank
        String dni,
        @Pattern(regexp = "^[0-9+]{1,13}$")
        @NotBlank
        String phone,
        @Schema(description = "Birthdate is optional -> The format is dd/MM/yyyy")
        @DateTimeFormat(pattern = "dd/MM/yyyy")
        @Nullable
        LocalDate birthDate,
        @Email
        @NotBlank
        String email,
        @NotBlank
        String password

) {

}
