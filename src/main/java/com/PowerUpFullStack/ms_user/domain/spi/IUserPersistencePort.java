package com.PowerUpFullStack.ms_user.domain.spi;

import com.PowerUpFullStack.ms_user.domain.models.User;

public interface IUserPersistencePort {
    void saveUser(User user);
}
