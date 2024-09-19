package com.PowerUpFullStack.ms_user.configuration.security.jwt.utils;

public class ConstantsJwt {
    private ConstantsJwt() { throw new IllegalStateException("Utility class"); }

    public static final String CLAIM_KEY_ROLES = "roles";
    public static final String CLAIM_KEY_ID = "id";

    public static final String MALFORMED_JWT_TOKEN_ERROR_MESSAGE = "Malformed token";
    public static final String EXPIRED_JWT_TOKEN_ERROR_MESSAGE = "Expired token";
    public static final String UNSUPPORTED_JWT_TOKEN_ERROR_MESSAGE = "Unsupported token";
    public static final String EMPTY_JWT_TOKEN_ERROR_MESSAGE = "Empty token";
    public static final String SIGNATURE_JWT_TOKEN_ERROR_MESSAGE = "Signature failed";
}
