package com.PowerUpFullStack.ms_user.configuration;

public class Constants {
    private Constants() { throw new IllegalStateException("Utility class"); }

    public static final String DNI_ONLY_CONTAIN_NUMBERS_MESSAGE_EXCEPTION = "Dni only contain numbers";
    public static final String DNI_NOT_VALID_MESSAGE_EXCEPTION = "Dni not valid";
    public static final String EMAIL_ALREADY_EXISTS_MESSAGE_EXCEPTION = "Email already exists";
    public static final String USER_ALREADY_EXISTS_MESSAGE_EXCEPTION = "User already exists";
    public static final String EMAIL_NOT_VALID_MESSAGE_EXCEPTION = "Email not valid";
    public static final String PHONE_NUMBER_NOT_VALID_MESSAGE_EXCEPTION = "Phone number not valid";
    public static final String AGE_NOT_VALID_MESSAGE_EXCEPTION = "Age not valid";
    public static final int AGE_ALLOWED = 18;
    public static final String RESPONSE_ERROR_MESSAGE = "Error:";
    public static final String SWAGGER_TITLE_MESSAGE = "User API Pragma Power Up Full Stack";
    public static final String SWAGGER_DESCRIPTION_MESSAGE = "User microservice";
    public static final String SWAGGER_VERSION_MESSAGE = "1.0.0";
    public static final String SWAGGER_LICENSE_NAME_MESSAGE = "Apache 2.0";
    public static final String SWAGGER_LICENSE_URL_MESSAGE = "http://springdoc.org";
    public static final String SWAGGER_TERMS_OF_SERVICE_MESSAGE = "http://swagger.io/terms/";
}
