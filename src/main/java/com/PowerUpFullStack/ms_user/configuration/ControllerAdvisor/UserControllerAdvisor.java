package com.PowerUpFullStack.ms_user.configuration.ControllerAdvisor;

import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.exceptions.EmailAlreadyExistsException;
import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.exceptions.UserAlreadyExistsException;
import com.PowerUpFullStack.ms_user.domain.exceptions.AgeNotValidException;
import com.PowerUpFullStack.ms_user.domain.exceptions.DniIsNotValidException;
import com.PowerUpFullStack.ms_user.domain.exceptions.DniOnlyContainNumbersException;
import com.PowerUpFullStack.ms_user.domain.exceptions.EmailNotValidException;
import com.PowerUpFullStack.ms_user.domain.exceptions.PhoneNumberIsNotValidException;
import com.PowerUpFullStack.ms_user.domain.exceptions.RoleIsInvalidPermissionCreateUserException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.PowerUpFullStack.ms_user.configuration.Constants.AGE_NOT_VALID_MESSAGE_EXCEPTION;
import static com.PowerUpFullStack.ms_user.configuration.Constants.DNI_NOT_VALID_MESSAGE_EXCEPTION;
import static com.PowerUpFullStack.ms_user.configuration.Constants.DNI_ONLY_CONTAIN_NUMBERS_MESSAGE_EXCEPTION;
import static com.PowerUpFullStack.ms_user.configuration.Constants.EMAIL_ALREADY_EXISTS_MESSAGE_EXCEPTION;
import static com.PowerUpFullStack.ms_user.configuration.Constants.EMAIL_NOT_VALID_MESSAGE_EXCEPTION;
import static com.PowerUpFullStack.ms_user.configuration.Constants.PHONE_NUMBER_NOT_VALID_MESSAGE_EXCEPTION;
import static com.PowerUpFullStack.ms_user.configuration.Constants.RESPONSE_ERROR_MESSAGE;
import static com.PowerUpFullStack.ms_user.configuration.Constants.USER_ALREADY_EXISTS_MESSAGE_EXCEPTION;

@ControllerAdvice
public class UserControllerAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errorMessages = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                errorMessages.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
            } else {
                errorMessages.add(error.getDefaultMessage());
            }
        }
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.put(propertyPath, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AgeNotValidException.class)
    public ResponseEntity<Map<String, String>> handleAgeNotValidException(AgeNotValidException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, AGE_NOT_VALID_MESSAGE_EXCEPTION));
    }

    @ExceptionHandler(PhoneNumberIsNotValidException.class)
    public ResponseEntity<Map<String, String>> handlePhoneNumberIsNotValidException(PhoneNumberIsNotValidException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, PHONE_NUMBER_NOT_VALID_MESSAGE_EXCEPTION));
    }
    @ExceptionHandler(EmailNotValidException.class)
    public ResponseEntity<Map<String, String>> handleEmailNotValidException(EmailNotValidException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, EMAIL_NOT_VALID_MESSAGE_EXCEPTION));
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExistsException(UserAlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, USER_ALREADY_EXISTS_MESSAGE_EXCEPTION));
    }
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, EMAIL_ALREADY_EXISTS_MESSAGE_EXCEPTION));
    }
    @ExceptionHandler(DniIsNotValidException.class)
    public ResponseEntity<Map<String, String>> handleDniIsNotValidException(DniIsNotValidException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, DNI_NOT_VALID_MESSAGE_EXCEPTION));
    }
    @ExceptionHandler(DniOnlyContainNumbersException.class)
    public ResponseEntity<Map<String, String>> handleDniOnlyContainNumbersException(DniOnlyContainNumbersException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, DNI_ONLY_CONTAIN_NUMBERS_MESSAGE_EXCEPTION));
    }

    @ExceptionHandler(RoleIsInvalidPermissionCreateUserException.class)
    public ResponseEntity<Map<String, String>> handleRoleIsInvalidPermissionCreateUserException(RoleIsInvalidPermissionCreateUserException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, ex.getMessage()));
    }
}
