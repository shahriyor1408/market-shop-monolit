package com.company.telegrambotapp.config.security;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 07/11/22 Monday 09:19
 * telegram-bot-app/IntelliJ IDEA
 */

public class SecurityConstants {
    public static final String[] WHITE_LIST = {
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/api/v1/product/get/**",
            "/api/v1/product/**",
            "/api/v1/category/getAll",
            "/home", "/api/v1/search",
            "/swagger-ui/**",
            "/api-docs"
    };
}
