package com.PowerUpFullStack.ms_user.User;

import com.PowerUpFullStack.ms_user.domain.exceptions.AgeNotValidException;
import com.PowerUpFullStack.ms_user.domain.exceptions.DniOnlyContainNumbersException;
import com.PowerUpFullStack.ms_user.domain.exceptions.EmailNotValidException;
import com.PowerUpFullStack.ms_user.domain.exceptions.PhoneNumberIsNotValidException;
import com.PowerUpFullStack.ms_user.domain.models.Role;
import com.PowerUpFullStack.ms_user.domain.models.User;
import com.PowerUpFullStack.ms_user.domain.spi.IRolePersistencePort;
import com.PowerUpFullStack.ms_user.domain.spi.IUserPersistencePort;
import com.PowerUpFullStack.ms_user.domain.usecase.UserUseCase;
import com.PowerUpFullStack.ms_user.domain.usecase.utils.UserUseCaseUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestPropertySource(locations = "classpath:application-dev.yml")
@SpringBootTest
public class UserUseCaseTest {
    @Mock
    private IUserPersistencePort userPersistencePort;
    @Mock
    private IRolePersistencePort rolePersistencePort;
    @Mock
    private UserUseCaseUtils userUseCaseUtils;

    private UserUseCase userUseCase;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        userUseCase = new UserUseCase(userPersistencePort, rolePersistencePort, userUseCaseUtils);
    }

    @Test
    public void createUser_ShouldSaveUser_WhenValidData() {
        User user = new User();
        user.setDni("12345678");
        user.setEmail("test@example.com");
        user.setPhone("+1234567890");
        user.setBirthDate(LocalDate.of(2000, 1, 1));

        Role role = new Role(2L, "User", "User role");
        when(rolePersistencePort.findRoleById(2L)).thenReturn(role);

        when(userUseCaseUtils.validateDniUser(any())).thenReturn(true);
        when(userUseCaseUtils.validateEmailUser(any())).thenReturn(true);
        when(userUseCaseUtils.validatePhoneNumberUser(any())).thenReturn(true);
        when(userUseCaseUtils.validateUserAge(any())).thenReturn(true);

        userUseCase.createUser(user);

        verify(userUseCaseUtils).validateDniUser("12345678");
        verify(userUseCaseUtils).validateEmailUser("test@example.com");
        verify(userUseCaseUtils).validatePhoneNumberUser("+1234567890");
        verify(userUseCaseUtils).validateUserAge(LocalDate.of(2000, 1, 1));
        verify(rolePersistencePort).findRoleById(2L);
        verify(userPersistencePort).saveUser(user);
    }


    @Test
    public void createUser_ShouldThrowException_WhenInvalidDni() {
        User user = new User();
        user.setDni("abc123");
        user.setEmail("test@example.com");
        user.setPhone("+1234567890");
        user.setBirthDate(LocalDate.of(2000, 1, 1));

        when(rolePersistencePort.findRoleById(2L)).thenReturn(new Role(2L, "User", "User role"));
        doThrow(new DniOnlyContainNumbersException()).when(userUseCaseUtils).validateDniUser(any());

        try {
            userUseCase.createUser(user);
        } catch (DniOnlyContainNumbersException e) {

        }

        verify(userUseCaseUtils).validateDniUser("abc123");
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    public void createUser_ShouldThrowException_WhenInvalidEmail() {
        User user = new User();
        user.setDni("12345678");
        user.setEmail("invalid-email");
        user.setPhone("+1234567890");
        user.setBirthDate(LocalDate.of(2000, 1, 1));

        when(rolePersistencePort.findRoleById(2L)).thenReturn(new Role(2L, "User", "User role"));
        doThrow(new EmailNotValidException()).when(userUseCaseUtils).validateEmailUser(any());

        try {
            userUseCase.createUser(user);
        } catch (EmailNotValidException e) {

        }

        verify(userUseCaseUtils).validateDniUser("12345678");
        verify(userUseCaseUtils).validateEmailUser("invalid-email");
        verify(userUseCaseUtils, never()).validatePhoneNumberUser(any());
        verify(userUseCaseUtils, never()).validateUserAge(any());
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    public void createUser_ShouldThrowException_WhenInvalidPhoneNumber() {
        User user = new User();
        user.setDni("12345678");
        user.setEmail("test@example.com");
        user.setPhone("invalid-phone");
        user.setBirthDate(LocalDate.of(2000, 1, 1));

        when(rolePersistencePort.findRoleById(2L)).thenReturn(new Role(2L, "User", "User role"));
        doThrow(new PhoneNumberIsNotValidException()).when(userUseCaseUtils).validatePhoneNumberUser(any());

        try {
            userUseCase.createUser(user);
        } catch (PhoneNumberIsNotValidException e) {
        }

        verify(userUseCaseUtils).validateDniUser("12345678");
        verify(userUseCaseUtils).validateEmailUser("test@example.com");
        verify(userUseCaseUtils).validatePhoneNumberUser("invalid-phone");
        verify(userUseCaseUtils, never()).validateUserAge(any());
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    public void createUser_ShouldThrowException_WhenInvalidAge() {
        User user = new User();
        user.setDni("12345678");
        user.setEmail("test@example.com");
        user.setPhone("+1234567890");
        user.setBirthDate(LocalDate.of(2010, 1, 1)); // Too young

        when(rolePersistencePort.findRoleById(2L)).thenReturn(new Role(2L, "User", "User role"));
        doThrow(new AgeNotValidException()).when(userUseCaseUtils).validateUserAge(any());

        try {
            userUseCase.createUser(user);
        } catch (AgeNotValidException e) {
        }

        verify(userUseCaseUtils).validateDniUser("12345678");
        verify(userUseCaseUtils).validateEmailUser("test@example.com");
        verify(userUseCaseUtils).validatePhoneNumberUser("+1234567890");
        verify(userUseCaseUtils).validateUserAge(LocalDate.of(2010, 1, 1));
        verify(userPersistencePort, never()).saveUser(any());
    }
}
