package com.PowerUpFullStack.ms_user.Auth;

import com.PowerUpFullStack.ms_user.adapters.driving.http.dtos.request.LoginRequestDto;
import com.PowerUpFullStack.ms_user.adapters.driving.http.dtos.response.JwtResponseDto;
import com.PowerUpFullStack.ms_user.adapters.driving.http.handlers.impl.AuthHandlerImpl;
import com.PowerUpFullStack.ms_user.configuration.security.jwt.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestPropertySource(locations = "classpath:application-dev.yml")
@SpringBootTest
public class AuthServiceTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetails userDetails;

    private AuthHandlerImpl authHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        authHandler = new AuthHandlerImpl(authenticationManager, jwtProvider);
    }

    @Test
    public void testLogin_Success() {
        LoginRequestDto loginRequestDto = new LoginRequestDto("john.doe@example.com", "password");
        String jwtToken = "fake-jwt-token";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtProvider.generateToken(authentication)).thenReturn(jwtToken);

        JwtResponseDto jwtResponseDto = authHandler.login(loginRequestDto);

        assertEquals(jwtToken, jwtResponseDto.getToken());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtProvider, times(1)).generateToken(authentication);
    }

    @Test
    public void testLogin_Failure() {
        LoginRequestDto loginRequestDto = new LoginRequestDto("john.doe@example.com", "wrong-password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Authentication failed"));

        try {
            authHandler.login(loginRequestDto);
        } catch (RuntimeException ex) {
            assertEquals("Authentication failed", ex.getMessage());
        }

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtProvider, times(0)).generateToken(any(Authentication.class));
    }

    @Test
    public void testRefresh_Success() throws ParseException {
        JwtResponseDto oldJwtResponseDto = new JwtResponseDto("old-fake-jwt-token");
        String newJwtToken = "new-fake-jwt-token";

        when(jwtProvider.refreshToken(any(JwtResponseDto.class))).thenReturn(newJwtToken);

        JwtResponseDto refreshedJwtResponseDto = authHandler.refresh(oldJwtResponseDto);

        assertEquals(newJwtToken, refreshedJwtResponseDto.getToken());
        verify(jwtProvider, times(1)).refreshToken(any(JwtResponseDto.class));
    }

    @Test
    public void testRefresh_InvalidToken() throws ParseException {
        JwtResponseDto oldJwtResponseDto = new JwtResponseDto("invalid-jwt-token");

        try {
            when(jwtProvider.refreshToken(any(JwtResponseDto.class)))
                    .thenThrow(new ParseException("Invalid token", 0));

            authHandler.refresh(oldJwtResponseDto);
        } catch (ParseException ex) {
            assertEquals("Invalid token", ex.getMessage());
        }

        verify(jwtProvider, times(1)).refreshToken(any(JwtResponseDto.class));
    }


}
