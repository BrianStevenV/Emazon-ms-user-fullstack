package com.PowerUpFullStack.ms_user.domain.usecase.utils;

import com.PowerUpFullStack.ms_user.configuration.Constants;
import com.PowerUpFullStack.ms_user.domain.exceptions.AgeNotValidException;
import com.PowerUpFullStack.ms_user.domain.exceptions.DniIsNotValidException;
import com.PowerUpFullStack.ms_user.domain.exceptions.DniOnlyContainNumbersException;
import com.PowerUpFullStack.ms_user.domain.exceptions.EmailNotValidException;
import com.PowerUpFullStack.ms_user.domain.exceptions.PhoneNumberIsNotValidException;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class UserUseCaseUtils {
    public UserUseCaseUtils(){};

    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^\\+?[0-9]{1,12}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

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
        if (!dni.matches("\\d+")) { // Verifica si toda la cadena contiene solo dígitos
            throw new DniOnlyContainNumbersException();
        }
        return true;
    }


    private int calculateAge(LocalDate birthdate) {
        return Period.between(birthdate, LocalDate.now()).getYears();
    }
}
