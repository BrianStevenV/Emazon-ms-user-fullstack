package com.PowerUpFullStack.ms_user.configuration.OpenApiConfig;

public class OpenApiConstants {
    private OpenApiConstants() { throw new IllegalStateException("Utility class"); }

    public static final String CODE_201 = "201";
    public static final String CODE_409 = "409";

    // User Rest Controller

    public static final String SUMMARY_CREATE_WAREHOUSE = "Add a new Warehouse user";
    public static final String DESCRIPTION_CREATE_WAREHOUSE_201 = "Warehouse user created";
    public static final String DESCRIPTION_CREATE_WAREHOUSE_409 = "Warehouse user exists";

    // Content

    public static final String APPLICATION_JSON = "application/json";

    // Security

    public static final String SECURITY_REQUIREMENT = "jwt";

    // Schema

    public static final String SCHEMAS_MAP = "#/components/schemas/Map";
    public static final String SCHEMAS_ERROR = "#/components/schemas/Error";
}
