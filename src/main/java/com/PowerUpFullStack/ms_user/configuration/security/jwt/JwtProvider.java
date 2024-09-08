package com.PowerUpFullStack.ms_user.configuration.security.jwt;

import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.entities.PrincipalUser;
import com.PowerUpFullStack.ms_user.adapters.driving.http.dtos.response.JwtResponseDto;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.text.ParseException;
import java.util.Date;

import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;

@Component
public class JwtProvider {

    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication authentication) {
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principalUser.getUsername())
                .claim("roles", principalUser.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList())
                .claim("id", principalUser.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 180))
                .signWith(hmacShaKeyFor(secret.getBytes()))
                .compact();
    }


    public String refreshToken(JwtResponseDto jwtResponseDto) throws ParseException {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret.getBytes())
                    .build()
                    .parseClaimsJws(jwtResponseDto.getToken());
        } catch (ExpiredJwtException e) {
            JWTClaimsSet claims = JWTParser.parse(jwtResponseDto.getToken()).getJWTClaimsSet();
            return Jwts.builder()
                    .setSubject(claims.getSubject())
                    .claim("roles", claims.getStringListClaim("roles"))
                    .claim("id", claims.getStringListClaim("id"))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 180))
                    .signWith(hmacShaKeyFor(secret.getBytes()))
                    .compact();
        }
        return jwtResponseDto.getToken();
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Malformed token");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported token");
        } catch (ExpiredJwtException e) {
            logger.error("Expired token");
        } catch (IllegalArgumentException e) {
            logger.error("Empty token");
        } catch (SignatureException e) {
            logger.error("Signature failed");
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().getSubject();
    }

}
