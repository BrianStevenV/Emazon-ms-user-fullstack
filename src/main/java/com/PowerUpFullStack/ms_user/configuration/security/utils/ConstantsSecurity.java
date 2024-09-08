package com.PowerUpFullStack.ms_user.configuration.security.utils;

public class ConstantsSecurity {

    private ConstantsSecurity() { throw new IllegalStateException("Utility class"); }

    public static final String ADMINISTRATOR_ROLE = "ADMINISTRATOR_ROLE";
    public static final String USERS_CONTROLLER_POST_WAREHOUSE = "/api/v1/users/warehouse";
    public static final String AUTH_CONTROLLER_POST_LOGIN = "/api/v1/auth/login";
    public static final String AUTH_CONTROLLER_POST_REFRESH = "/api/v1/auth/refresh";

    public static final String ACTUATOR_HEALTH = "/actuator/health";

    public static final String SWAGGER_UI_HTML = "/swagger-ui.html";
    public static final String SWAGGER_UI = "/swagger-ui/**";
    public static final String V3_API_DOCS = "/v3/api-docs/**";
}
