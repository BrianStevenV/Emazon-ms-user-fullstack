package com.PowerUpFullStack.ms_user.adapters.driving.http.utils;

public class AuthControllerConstants {
    private AuthControllerConstants() { throw new IllegalStateException("Utility class"); }

    public static final String AUTH_CONTROLLER_REQUEST_MAPPING = "/api/v1/auth";

    public static final String AUTH_CONTROLLER_POST_LOGIN = "/login";
    public static final String AUTH_CONTROLLER_POST_REFRESH = "/refresh";

}
