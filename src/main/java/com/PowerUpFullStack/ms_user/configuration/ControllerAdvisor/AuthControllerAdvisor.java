package com.PowerUpFullStack.ms_user.configuration.ControllerAdvisor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

import static com.PowerUpFullStack.ms_user.configuration.Constants.RESPONSE_ERROR_MESSAGE;
import static com.PowerUpFullStack.ms_user.configuration.Constants.WRONG_CREDENTIALS_MESSAGE;

@ControllerAdvice
public class AuthControllerAdvisor {
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationException(AuthenticationException noDataFoundException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, WRONG_CREDENTIALS_MESSAGE));
    }
}
