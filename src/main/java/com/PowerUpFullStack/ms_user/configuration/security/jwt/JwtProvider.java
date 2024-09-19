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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

import static com.PowerUpFullStack.ms_user.configuration.security.jwt.utils.ConstantsJwt.CLAIM_KEY_ID;
import static com.PowerUpFullStack.ms_user.configuration.security.jwt.utils.ConstantsJwt.CLAIM_KEY_ROLES;
import static com.PowerUpFullStack.ms_user.configuration.security.jwt.utils.ConstantsJwt.EMPTY_JWT_TOKEN_ERROR_MESSAGE;
import static com.PowerUpFullStack.ms_user.configuration.security.jwt.utils.ConstantsJwt.EXPIRED_JWT_TOKEN_ERROR_MESSAGE;
import static com.PowerUpFullStack.ms_user.configuration.security.jwt.utils.ConstantsJwt.MALFORMED_JWT_TOKEN_ERROR_MESSAGE;
import static com.PowerUpFullStack.ms_user.configuration.security.jwt.utils.ConstantsJwt.SIGNATURE_JWT_TOKEN_ERROR_MESSAGE;
import static com.PowerUpFullStack.ms_user.configuration.security.jwt.utils.ConstantsJwt.UNSUPPORTED_JWT_TOKEN_ERROR_MESSAGE;
import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;

@Component
public class JwtProvider {

    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    private JwtConfig jwtConfig;

    public JwtProvider(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String generateToken(Authentication authentication) {
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principalUser.getUsername())
                .claim(CLAIM_KEY_ROLES, principalUser.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList())
                .claim(CLAIM_KEY_ID, principalUser.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration() * 180))
                .signWith(hmacShaKeyFor(jwtConfig.getSecret().getBytes()))
                .compact();
    }


    public String refreshToken(JwtResponseDto jwtResponseDto) throws ParseException {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtConfig.getSecret().getBytes())
                    .build()
                    .parseClaimsJws(jwtResponseDto.getToken());
        } catch (ExpiredJwtException e) {
            JWTClaimsSet claims = JWTParser.parse(jwtResponseDto.getToken()).getJWTClaimsSet();
            return Jwts.builder()
                    .setSubject(claims.getSubject())
                    .claim(CLAIM_KEY_ROLES, claims.getStringListClaim(CLAIM_KEY_ROLES))
                    .claim(CLAIM_KEY_ID, claims.getStringListClaim(CLAIM_KEY_ID))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration() * 180))
                    .signWith(hmacShaKeyFor(jwtConfig.getSecret().getBytes()))
                    .compact();
        }
        return jwtResponseDto.getToken();
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtConfig.getSecret().getBytes()).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error(MALFORMED_JWT_TOKEN_ERROR_MESSAGE);
        } catch (UnsupportedJwtException e) {
            logger.error(UNSUPPORTED_JWT_TOKEN_ERROR_MESSAGE);
        } catch (ExpiredJwtException e) {
            logger.error(EXPIRED_JWT_TOKEN_ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            logger.error(EMPTY_JWT_TOKEN_ERROR_MESSAGE);
        } catch (SignatureException e) {
            logger.error(SIGNATURE_JWT_TOKEN_ERROR_MESSAGE);
        }
        return false;
    }



}
