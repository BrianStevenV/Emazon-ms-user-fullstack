package com.PowerUpFullStack.ms_user.adapters.driving.http.controller;

import com.PowerUpFullStack.ms_user.adapters.driving.http.dtos.request.UserRequestDto;
import com.PowerUpFullStack.ms_user.adapters.driving.http.handlers.IUserHandler;
import com.PowerUpFullStack.ms_user.adapters.driving.http.utils.UserRestControllerConstants;
import com.PowerUpFullStack.ms_user.configuration.Constants;
import com.PowerUpFullStack.ms_user.configuration.OpenApiConfig.OpenApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UserRestControllerConstants.USER_REST_CONTROLLER_BASE_PATH)
@RequiredArgsConstructor
public class UserRestController {
    private final IUserHandler userHandler;

    @Operation(summary = OpenApiConstants.SUMMARY_CREATE_WAREHOUSE,
            responses = {
                    @ApiResponse(responseCode = OpenApiConstants.CODE_201, description = OpenApiConstants.DESCRIPTION_CREATE_WAREHOUSE_201,
                            content = @Content(mediaType = OpenApiConstants.APPLICATION_JSON, schema = @Schema(ref = OpenApiConstants.SCHEMAS_MAP))),
                    @ApiResponse(responseCode = OpenApiConstants.CODE_409, description = OpenApiConstants.DESCRIPTION_CREATE_WAREHOUSE_409,
                            content = @Content(mediaType = OpenApiConstants.APPLICATION_JSON, schema = @Schema(ref = OpenApiConstants.SCHEMAS_ERROR)))})
    @PostMapping(UserRestControllerConstants.USER_REST_CONTROLLER_POST_WAREHOUSE)
    @SecurityRequirement(name = OpenApiConstants.SECURITY_REQUIREMENT)
    public ResponseEntity<Void> createAuxiliaryWarehouseUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        userHandler.createUser(userRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
