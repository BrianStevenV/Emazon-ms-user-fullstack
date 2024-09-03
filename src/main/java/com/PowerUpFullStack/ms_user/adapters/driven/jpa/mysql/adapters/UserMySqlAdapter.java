package com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.adapters;

import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.exceptions.EmailAlreadyExistsException;
import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.exceptions.UserAlreadyExistsException;
import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.mappers.IUserEntityMapper;
import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.repositories.IUserRepository;
import com.PowerUpFullStack.ms_user.domain.models.User;
import com.PowerUpFullStack.ms_user.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class UserMySqlAdapter implements IUserPersistencePort {
    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        if (userRepository.existsByEmail(user.getEmail())){
            throw new EmailAlreadyExistsException();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(userEntityMapper.toUserEntity(user));
    }
}
