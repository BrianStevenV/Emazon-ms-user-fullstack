package com.PowerUpFullStack.ms_user.User;

import com.PowerUpFullStack.ms_user.configuration.Constants;
import com.PowerUpFullStack.ms_user.configuration.security.IUserContextService;
import com.PowerUpFullStack.ms_user.domain.exceptions.AgeNotValidException;
import com.PowerUpFullStack.ms_user.domain.exceptions.DniIsNotValidException;
import com.PowerUpFullStack.ms_user.domain.exceptions.DniOnlyContainNumbersException;
import com.PowerUpFullStack.ms_user.domain.exceptions.EmailNotValidException;
import com.PowerUpFullStack.ms_user.domain.exceptions.PhoneNumberIsNotValidException;
import com.PowerUpFullStack.ms_user.domain.exceptions.RoleIsInvalidPermissionCreateUserException;
import com.PowerUpFullStack.ms_user.domain.usecase.utils.ConstantsUserUseCase;
import com.PowerUpFullStack.ms_user.domain.usecase.utils.UserUseCaseUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.PowerUpFullStack.ms_user.configuration.Constants.ROLE_IS_NOT_AUTHORIZED_CREATE_USER_MESSAGE_EXCEPTION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestPropertySource(locations = "classpath:application-dev.yml")
@SpringBootTest
public class UserUseCaseUtilsTest {
    @Mock
    private IUserContextService userContextService;

    @InjectMocks
    private UserUseCaseUtils userUseCaseUtils;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidateUserAge_ValidAge() {
        LocalDate birthdate = LocalDate.now().minusYears(Constants.AGE_ALLOWED + 1);
        assertTrue(userUseCaseUtils.validateUserAge(birthdate));
    }

    @Test
    public void testValidateUserAge_InvalidAge() {
        LocalDate birthdate = LocalDate.now().minusYears(Constants.AGE_ALLOWED - 1);
        assertThrows(AgeNotValidException.class, () -> userUseCaseUtils.validateUserAge(birthdate));
    }

    @Test
    public void testValidatePhoneNumberUser_ValidNumber() {
        String phoneNumber = "+1234567890";
        assertTrue(userUseCaseUtils.validatePhoneNumberUser(phoneNumber));
    }

    @Test
    public void testValidatePhoneNumberUser_InvalidNumber() {
        String phoneNumber = "1234ABCD";
        assertThrows(PhoneNumberIsNotValidException.class, () -> userUseCaseUtils.validatePhoneNumberUser(phoneNumber));
    }

    @Test
    public void testValidateEmailUser_ValidEmail() {
        String email = "test@example.com";
        assertTrue(userUseCaseUtils.validateEmailUser(email));
    }

    @Test
    public void testValidateEmailUser_InvalidEmail() {
        String email = "invalid-email";
        assertThrows(EmailNotValidException.class, () -> userUseCaseUtils.validateEmailUser(email));
    }

    @Test
    public void testValidateDniUser_ValidDni() {
        String dni = "12345678";
        assertTrue(userUseCaseUtils.validateDniUser(dni));
    }

    @Test
    public void testValidateDniUser_InvalidDni() {
        assertThrows(DniIsNotValidException.class, () -> userUseCaseUtils.validateDniUser(null));
        assertThrows(DniIsNotValidException.class, () -> userUseCaseUtils.validateDniUser(""));
        assertThrows(DniOnlyContainNumbersException.class, () -> userUseCaseUtils.validateDniUser("1234ABCD"));
    }

    // Aqui


    @Test
    void whenRoleIsAdministrator_thenReturnsWarehouseAssistantRole() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        GrantedAuthority authority = new SimpleGrantedAuthority(ConstantsUserUseCase.ADMINISTRATOR_ROLE);

        List<? extends GrantedAuthority> authorities = Collections.singletonList(authority);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        doReturn(authorities).when(authentication).getAuthorities();

        // Usar MockedStatic para simular el comportamiento estático de SecurityContextHolder
        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class)) {
            mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            // Simular el comportamiento de getRoleFromUserContextService para devolver el rol de administrador
            UserUseCaseUtils userUseCaseUtils = spy(new UserUseCaseUtils(userContextService));
            doReturn(ConstantsUserUseCase.ADMINISTRATOR_ROLE).when(userUseCaseUtils).getRoleFromUserContextService();

            // Actuar - invocar el método de validación
            String result = userUseCaseUtils.validationPermissionCreateUser();

            // Assert - verificar el resultado esperado
            assertEquals(ConstantsUserUseCase.WAREHOUSE_ASSISTANT_ROLE, result);
        }
    }

    @Test
    void whenRoleIsAnonymousUser_thenReturnsCustomerRole() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        GrantedAuthority authority = new SimpleGrantedAuthority(ConstantsUserUseCase.ANONYMOUS_USER);

        List<? extends GrantedAuthority> authorities = Collections.singletonList(authority);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        doReturn(authorities).when(authentication).getAuthorities();

        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class)) {
            mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            UserUseCaseUtils userUseCaseUtils = spy(new UserUseCaseUtils(userContextService));
            doReturn(ConstantsUserUseCase.ANONYMOUS_USER).when(userUseCaseUtils).getRoleFromUserContextService();

            String result = userUseCaseUtils.validationPermissionCreateUser();

            assertEquals(ConstantsUserUseCase.CUSTOMER_ROLE, result);
        }
    }


    @Test
    void whenRoleIsInvalid_thenThrowsRoleIsInvalidPermissionCreateUserException() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        List<? extends GrantedAuthority> authorities = Collections.emptyList();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        doReturn(authorities).when(authentication).getAuthorities();

        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class)) {
            mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            IUserContextService userContextService = mock(IUserContextService.class);
            doReturn("INVALID_ROLE").when(userContextService).getAuthenticationUserRole();

            UserUseCaseUtils userUseCaseUtils = new UserUseCaseUtils(userContextService);

            RoleIsInvalidPermissionCreateUserException exception = assertThrows(
                    RoleIsInvalidPermissionCreateUserException.class,
                    () -> userUseCaseUtils.validationPermissionCreateUser()
            );

            assertEquals(ROLE_IS_NOT_AUTHORIZED_CREATE_USER_MESSAGE_EXCEPTION, exception.getMessage());

            verify(userContextService, times(1)).getAuthenticationUserRole();
        }
    }

}
