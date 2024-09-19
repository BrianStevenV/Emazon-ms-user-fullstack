package com.PowerUpFullStack.ms_user.configuration;

import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.adapters.RoleMySqlAdapter;
import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.adapters.UserMySqlAdapter;
import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.mappers.IRoleEntityMapper;
import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.mappers.IUserEntityMapper;
import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.repositories.IRoleRepository;
import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.repositories.IUserRepository;
import com.PowerUpFullStack.ms_user.configuration.security.IUserContextService;
import com.PowerUpFullStack.ms_user.domain.api.IUserServicePort;
import com.PowerUpFullStack.ms_user.domain.spi.IRolePersistencePort;
import com.PowerUpFullStack.ms_user.domain.spi.IUserPersistencePort;
import com.PowerUpFullStack.ms_user.domain.usecase.UserUseCase;
import com.PowerUpFullStack.ms_user.domain.usecase.utils.UserUseCaseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;

    private final IUserContextService userContextService;


    private final PasswordEncoder passwordEncoder;

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserMySqlAdapter(userRepository, userEntityMapper, passwordEncoder);
    }
    @Bean
    public IUserServicePort userServicePort(){
        return new UserUseCase(userPersistencePort(), rolePersistencePort(), userUseCaseUtils());
    }

    @Bean
    public IRolePersistencePort rolePersistencePort() {
        return new RoleMySqlAdapter(roleRepository, roleEntityMapper);
    }

    @Bean
    public UserUseCaseUtils userUseCaseUtils() {
        return new UserUseCaseUtils(userContextService);
    }
}
