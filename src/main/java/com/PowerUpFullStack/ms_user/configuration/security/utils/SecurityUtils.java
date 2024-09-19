package com.PowerUpFullStack.ms_user.configuration.security.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.AntPathMatcher;

import java.util.List;
import java.util.stream.Collectors;

import static com.PowerUpFullStack.ms_user.configuration.security.utils.ConstantsSecurity.AUTHORIZATION_HEADER;
import static com.PowerUpFullStack.ms_user.configuration.security.utils.ConstantsSecurity.AUTHORIZATION_HEADER_SUBSTRING;
import static com.PowerUpFullStack.ms_user.configuration.security.utils.ConstantsSecurity.BEARER_TOKEN;
import static com.PowerUpFullStack.ms_user.configuration.security.utils.ConstantsSecurity.DELIMETER_JOINING_AUTH_GET_AUTHORITIES;

public class SecurityUtils {
    private SecurityUtils() {};

    private static AntPathMatcher pathMatcher = new AntPathMatcher();

    public static String getToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        if (header != null && header.startsWith(BEARER_TOKEN)) {
            return header.substring(AUTHORIZATION_HEADER_SUBSTRING);
        }
        return null;
    }

    public static String getRolesFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(DELIMETER_JOINING_AUTH_GET_AUTHORITIES));
    }

    public static boolean isExcludedPrefixRecursively(String currentRoute, List<String> prefixes) {
        if (prefixes.isEmpty()) {
            return false;
        }

        String prefix = prefixes.get(0);
        if (pathMatcher.matchStart(prefix, currentRoute)) {
            return true;
        } else {

            return isExcludedPrefixRecursively(currentRoute, prefixes.subList(1, prefixes.size()));
        }
    }

}
