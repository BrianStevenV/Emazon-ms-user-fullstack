package com.PowerUpFullStack.ms_user.adapters.driving.http.controller;

import com.PowerUpFullStack.ms_user.adapters.driving.http.dtos.request.UserRequestDto;
import com.PowerUpFullStack.ms_user.adapters.driving.http.handlers.IUserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {
    private final IUserHandler userHandler;

    @Operation(summary = "Add a new user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Person created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Person already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PostMapping("/")
    public ResponseEntity<Void> createAuxiliaryWarehouseUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        userHandler.createUser(userRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
