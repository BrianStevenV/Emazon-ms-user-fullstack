package com.PowerUpFullStack.ms_user.domain.usecase.utils;

import java.util.regex.Pattern;

public class ConstantsUserUseCase {
    private ConstantsUserUseCase() { throw new IllegalStateException("Utility class"); }

    public static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^\\+?[0-9]{1,12}$");
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    public static final String CONTAIN_ONLY_NUMBER = "\\d+";

    public static final String ADMINISTRATOR_ROLE = "ADMINISTRATOR_ROLE";
    public static final String WAREHOUSE_ASSISTANT_ROLE = "WAREHOUSE_ASSISTANT_ROLE";
    public static final String CUSTOMER_ROLE = "CUSTOMER_ROLE";
    public static final String ANONYMOUS_USER = "ROLE_ANONYMOUS";
}
