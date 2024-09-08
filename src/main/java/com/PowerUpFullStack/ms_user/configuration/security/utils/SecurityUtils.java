package com.PowerUpFullStack.ms_user.configuration.security.utils;

import jakarta.servlet.http.HttpServletRequest;

public class SecurityUtils {
    private SecurityUtils() {};

    public static String getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
