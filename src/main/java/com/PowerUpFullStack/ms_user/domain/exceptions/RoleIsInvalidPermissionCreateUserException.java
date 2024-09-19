package com.PowerUpFullStack.ms_user.domain.exceptions;

import org.springframework.security.access.AuthorizationServiceException;

public class RoleIsInvalidPermissionCreateUserException extends AuthorizationServiceException {
    public RoleIsInvalidPermissionCreateUserException(String msg) {
        super(msg);
    }
}
