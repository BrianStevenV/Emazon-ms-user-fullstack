package com.PowerUpFullStack.ms_user.domain.usecase;

import com.PowerUpFullStack.ms_user.domain.api.IUserServicePort;
import com.PowerUpFullStack.ms_user.domain.models.User;
import com.PowerUpFullStack.ms_user.domain.spi.IRolePersistencePort;
import com.PowerUpFullStack.ms_user.domain.spi.IUserPersistencePort;
import com.PowerUpFullStack.ms_user.domain.usecase.utils.UserUseCaseUtils;

public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;
    private final IRolePersistencePort rolePersistencePort;
    private final UserUseCaseUtils userUseCaseUtils;

    public UserUseCase(IUserPersistencePort userPersistencePort,
                       IRolePersistencePort rolePersistencePort,
                       UserUseCaseUtils userUseCaseUtils) {
        this.userPersistencePort = userPersistencePort;
        this.rolePersistencePort = rolePersistencePort;
        this.userUseCaseUtils = userUseCaseUtils;
    }

    @Override
    public void createUser(User user) {
        //Especificamente para la hu07, sin embargo, la implementacion cambia con otros roles.
        userUseCaseUtils.validateDniUser(user.getDni());
        userUseCaseUtils.validateEmailUser(user.getEmail());
        userUseCaseUtils.validatePhoneNumberUser(user.getPhone());
        userUseCaseUtils.validateUserAge(user.getBirthDate());
        user.setRoleId(rolePersistencePort.findRoleById(2L));
        userPersistencePort.saveUser(user);

    }
}
