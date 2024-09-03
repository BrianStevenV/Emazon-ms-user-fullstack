package com.PowerUpFullStack.ms_user.User;

import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.adapters.UserMySqlAdapter;
import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.entities.UserEntity;
import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.exceptions.EmailAlreadyExistsException;
import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.exceptions.UserAlreadyExistsException;
import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.mappers.IUserEntityMapper;
import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.repositories.IUserRepository;
import com.PowerUpFullStack.ms_user.domain.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestPropertySource(locations = "classpath:application-dev.yml")
@SpringBootTest
public class UserMySqlAdapterTest {
    @MockBean
    private IUserRepository userRepository;
    @MockBean
    private IUserEntityMapper userEntityMapper;
    @MockBean
    private PasswordEncoder passwordEncoder;

    private UserMySqlAdapter userMySqlAdapter;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        userMySqlAdapter = new UserMySqlAdapter(userRepository, userEntityMapper, passwordEncoder);
    }

    @Test
    public void saveUser_ShouldThrowUserAlreadyExistsException_WhenUserWithEmailExists() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new UserEntity()));

        // Act & Assert
        try {
            userMySqlAdapter.saveUser(user);
        } catch (UserAlreadyExistsException e) {
            verify(userRepository, times(1)).findByEmail("test@example.com");
            verify(userRepository, never()).existsByEmail(anyString());
            return;
        }
        throw new AssertionError("Expected UserAlreadyExistsException");
    }

    @Test
    public void saveUser_ShouldThrowEmailAlreadyExistsException_WhenEmailAlreadyExists() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // Act & Assert
        try {
            userMySqlAdapter.saveUser(user);
        } catch (EmailAlreadyExistsException e) {
            verify(userRepository, times(1)).findByEmail("test@example.com");
            verify(userRepository, times(1)).existsByEmail("test@example.com");
            return;
        }
        throw new AssertionError("Expected EmailAlreadyExistsException");
    }

    @Test
    public void saveUser_ShouldEncodePasswordAndSaveUser_WhenUserIsValid() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("plainPassword");

        UserEntity userEntity = new UserEntity();
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userEntityMapper.toUserEntity(any(User.class))).thenReturn(userEntity);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // Act
        userMySqlAdapter.saveUser(user);

        // Assert
        verify(passwordEncoder, times(1)).encode("plainPassword");
        verify(userRepository, times(1)).save(userEntity);
        assertEquals("encodedPassword", user.getPassword());
    }
}
