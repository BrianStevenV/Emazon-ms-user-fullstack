package com.PowerUpFullStack.ms_user.Auth;

import com.PowerUpFullStack.ms_user.adapters.driving.http.controller.AuthController;
import com.PowerUpFullStack.ms_user.adapters.driving.http.dtos.request.LoginRequestDto;
import com.PowerUpFullStack.ms_user.adapters.driving.http.dtos.response.JwtResponseDto;
import com.PowerUpFullStack.ms_user.adapters.driving.http.handlers.IAuthHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Validated
@TestPropertySource(locations = "classpath:application-dev.yml")
@WebMvcTest(controllers = AuthController.class)
public class AuthControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @MockBean
    private IAuthHandler authHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testLogin_Success() throws Exception {

        JwtResponseDto jwtResponseDto = new JwtResponseDto("fake-jwt-token");

        when(authHandler.login(any(LoginRequestDto.class))).thenReturn(jwtResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"john.doe@example.com\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("fake-jwt-token"));

        verify(authHandler, times(1)).login(any(LoginRequestDto.class));
    }

    @Test
    void testLogin_InvalidInput() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"\",\"password\":\"\"}"))
                .andExpect(status().isBadRequest());

        verify(authHandler, times(0)).login(any(LoginRequestDto.class));
    }

    @Test
    void testRefresh_Success() throws Exception {

        JwtResponseDto refreshedJwtResponseDto = new JwtResponseDto("refreshed-jwt-token");

        when(authHandler.refresh(any(JwtResponseDto.class))).thenReturn(refreshedJwtResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\":\"fake-jwt-token\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("refreshed-jwt-token"));

        verify(authHandler, times(1)).refresh(any(JwtResponseDto.class));
    }

}
