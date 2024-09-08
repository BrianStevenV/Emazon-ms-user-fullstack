package com.PowerUpFullStack.ms_user.configuration.security;

import com.PowerUpFullStack.ms_user.configuration.security.jwt.JwtEntryPoint;
import com.PowerUpFullStack.ms_user.configuration.security.utils.ConstantsSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurity {

    @Autowired
    JwtEntryPoint jwtEntryPoint;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(csrf -> csrf.disable())
                .formLogin( formLogin -> formLogin.disable())
                .sessionManagement( session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests( request -> request
                        .requestMatchers(ConstantsSecurity.SWAGGER_UI_HTML,ConstantsSecurity.SWAGGER_UI,
                                ConstantsSecurity.V3_API_DOCS, ConstantsSecurity.ACTUATOR_HEALTH,
                                ConstantsSecurity.AUTH_CONTROLLER_POST_LOGIN,
                                ConstantsSecurity.AUTH_CONTROLLER_POST_REFRESH).permitAll()
                        .requestMatchers(HttpMethod.POST, ConstantsSecurity.USERS_CONTROLLER_POST_WAREHOUSE)
                        .hasAuthority(ConstantsSecurity.ADMINISTRATOR_ROLE)
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtEntryPoint))
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    };

}
