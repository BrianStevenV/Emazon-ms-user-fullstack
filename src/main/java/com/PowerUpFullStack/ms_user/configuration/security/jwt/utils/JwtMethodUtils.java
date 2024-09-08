package com.PowerUpFullStack.ms_user.configuration.security.jwt.utils;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;

public class JwtMethodUtils {

    private JwtMethodUtils() { throw new IllegalStateException("Utility class"); }

    @Value("${jwt.secret}")
    private static String secret;

    public static String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().getSubject();
    }
}
