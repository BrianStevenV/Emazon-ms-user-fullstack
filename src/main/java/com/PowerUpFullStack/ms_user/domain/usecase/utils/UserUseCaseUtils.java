package com.PowerUpFullStack.ms_user.domain.usecase.utils;

import com.PowerUpFullStack.ms_user.configuration.Constants;
import com.PowerUpFullStack.ms_user.configuration.security.IUserContextService;
import com.PowerUpFullStack.ms_user.domain.exceptions.AgeNotValidException;
import com.PowerUpFullStack.ms_user.domain.exceptions.DniIsNotValidException;
import com.PowerUpFullStack.ms_user.domain.exceptions.DniOnlyContainNumbersException;
import com.PowerUpFullStack.ms_user.domain.exceptions.EmailNotValidException;
import com.PowerUpFullStack.ms_user.domain.exceptions.PhoneNumberIsNotValidException;
import com.PowerUpFullStack.ms_user.domain.exceptions.RoleIsInvalidPermissionCreateUserException;


import java.time.LocalDate;
import java.time.Period;


import static com.PowerUpFullStack.ms_user.configuration.Constants.ROLE_IS_NOT_AUTHORIZED_CREATE_USER_MESSAGE_EXCEPTION;
import static com.PowerUpFullStack.ms_user.domain.usecase.utils.ConstantsUserUseCase.ADMINISTRATOR_ROLE;
import static com.PowerUpFullStack.ms_user.domain.usecase.utils.ConstantsUserUseCase.ANONYMOUS_USER;
import static com.PowerUpFullStack.ms_user.domain.usecase.utils.ConstantsUserUseCase.CONTAIN_ONLY_NUMBER;
import static com.PowerUpFullStack.ms_user.domain.usecase.utils.ConstantsUserUseCase.CUSTOMER_ROLE;
import static com.PowerUpFullStack.ms_user.domain.usecase.utils.ConstantsUserUseCase.EMAIL_PATTERN;
import static com.PowerUpFullStack.ms_user.domain.usecase.utils.ConstantsUserUseCase.PHONE_NUMBER_PATTERN;
import static com.PowerUpFullStack.ms_user.domain.usecase.utils.ConstantsUserUseCase.WAREHOUSE_ASSISTANT_ROLE;

public class UserUseCaseUtils {
    private final IUserContextService userContextService;

    public UserUseCaseUtils(IUserContextService userContextService){
        this.userContextService = userContextService;
    };

    public boolean validateUserAge(LocalDate birthdate) {
        int age = calculateAge(birthdate);
        if (age >= Constants.AGE_ALLOWED) {
            return true;
        } else {
            throw new AgeNotValidException();
        }
    }

    public boolean validatePhoneNumberUser(String phoneNumber) {
        if (phoneNumber == null || !PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches()) {
            throw new PhoneNumberIsNotValidException();
        }
        return true;
    }

    public boolean validateEmailUser(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new EmailNotValidException();
        }
        return true;
    }

    public Boolean validateDniUser(String dni) {
        if (dni == null || dni.isEmpty()) {
            throw new DniIsNotValidException();
        }
        if (!dni.matches(CONTAIN_ONLY_NUMBER)) {
            throw new DniOnlyContainNumbersException();
        }
        return true;
    }

    public String validationPermissionCreateUser(){
        String role = getRoleFromUserContextService();
        if(ADMINISTRATOR_ROLE.equals(role))  return WAREHOUSE_ASSISTANT_ROLE;
        else if (role.equals(ANONYMOUS_USER)) return CUSTOMER_ROLE;
        else throw new RoleIsInvalidPermissionCreateUserException(ROLE_IS_NOT_AUTHORIZED_CREATE_USER_MESSAGE_EXCEPTION);
    }

    public String getRoleFromUserContextService(){
        return userContextService.getAuthenticationUserRole();
    }

    private int calculateAge(LocalDate birthdate) {
        return Period.between(birthdate, LocalDate.now()).getYears();
    }
}
