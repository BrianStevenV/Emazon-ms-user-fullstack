package com.PowerUpFullStack.ms_user.configuration.security.jwt.utils;

import com.PowerUpFullStack.ms_user.configuration.security.jwt.JwtConfig;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtMethodUtils {

    public JwtMethodUtils(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    private static JwtConfig jwtConfig;

    public static String getUsernameFromToken(String token) {
        String secret = jwtConfig.getSecret();
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().getSubject();
    }

}
