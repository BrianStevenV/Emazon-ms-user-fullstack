package com.PowerUpFullStack.ms_user.User;

import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.exceptions.UserAlreadyExistsException;
import com.PowerUpFullStack.ms_user.adapters.driving.http.controller.UserRestController;
import com.PowerUpFullStack.ms_user.adapters.driving.http.dtos.request.UserRequestDto;
import com.PowerUpFullStack.ms_user.adapters.driving.http.handlers.IUserHandler;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Validated
@TestPropertySource(locations = "classpath:application-dev.yml")
@WebMvcTest(controllers = UserRestController.class)
public class UserRestControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    @MockBean
    private IUserHandler userHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    public void testCreateUser_Success() throws Exception {
        UserRequestDto userRequestDto = new UserRequestDto(
                "John", "Doe", "12345678", "1234567890", null,
                "john.doe@example.com", "password");

        doNothing().when(userHandler).createUser(any(UserRequestDto.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\",\"surName\":\"Doe\",\"dni\":\"12345678\",\"phone\":\"1234567890\",\"email\":\"john.doe@example.com\",\"password\":\"password\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateUser_UserAlreadyExists() throws Exception {
        UserRequestDto userRequestDto = new UserRequestDto(
                "John", "Doe", "12345678", "1234567890", null,
                "john.doe@example.com", "password");

        doThrow(new UserAlreadyExistsException()).when(userHandler).createUser(any(UserRequestDto.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\",\"surName\":\"Doe\",\"dni\":\"12345678\",\"phone\":\"1234567890\",\"email\":\"john.doe@example.com\",\"password\":\"password\"}"))
                .andExpect(status().isConflict());
    }

    @Test
    public void testCreateUser_InvalidInput() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"surName\":\"\",\"dni\":\"\",\"phone\":\"\",\"email\":\"not-an-email\",\"password\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(status().isBadRequest());
    }
}
