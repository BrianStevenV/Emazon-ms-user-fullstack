package com.PowerUpFullStack.ms_user.configuration.security;

import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.adapters.UserDetailsServiceImpl;
import com.PowerUpFullStack.ms_user.configuration.security.jwt.utils.JwtMethodUtils;
import com.PowerUpFullStack.ms_user.configuration.security.jwt.JwtProvider;
import com.PowerUpFullStack.ms_user.configuration.security.utils.ConstantsSecurity;
import com.PowerUpFullStack.ms_user.configuration.security.utils.SecurityUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.PowerUpFullStack.ms_user.configuration.security.utils.SecurityUtils.getToken;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    private List<String> excludedPrefixes = Arrays.asList(ConstantsSecurity.SWAGGER_UI_HTML,
            ConstantsSecurity.SWAGGER_UI, ConstantsSecurity.V3_API_DOCS,
            ConstantsSecurity.ACTUATOR_HEALTH,
            ConstantsSecurity.AUTH_CONTROLLER_POST_LOGIN,
            ConstantsSecurity.AUTH_CONTROLLER_POST_REFRESH,
            ConstantsSecurity.USERS_CONTROLLER_POST_CUSTOMER);


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = getToken(request);

        if(token != null && jwtProvider.validateToken(token)){

            String username = JwtMethodUtils.getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
                    userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String currentRoute = request.getServletPath();
        return SecurityUtils.isExcludedPrefixRecursively(currentRoute, excludedPrefixes);
    }


}
