package com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.entities.utils;

public class EntityConstant {
    private EntityConstant() { throw new IllegalStateException("Utility class"); }

    public static final String USER_ENTITY_NAME = "user";
    public static final String ROLE_ENTITY_NAME = "role";

    // User Columns
    public static final String USER_COLUMN_NAME = "name";
    public static final String USER_COLUMN_SURNAME = "surname";
    public static final String USER_COLUMN_DNI = "dni";
    public static final String USER_COLUMN_PHONE = "phone";
    public static final String USER_COLUMN_BIRTHDATE = "birthdate";
    public static final String USER_COLUMN_EMAIL = "email";
    public static final String USER_COLUMN_PASSWORD = "password";
    public static final String USER_COLUMN_ROLE_ID = "id_rol";

    // Role Columns

    public static final String ROLE_COLUMN_NAME = "name";
    public static final String ROLE_COLUMN_DESCRIPTION = "description";
    public static final String ROLE_ONE_TO_MANY_MAPPED_BY = "roleId";
}
