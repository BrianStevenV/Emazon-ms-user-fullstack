package com.PowerUpFullStack.ms_user.domain.api;

import com.PowerUpFullStack.ms_user.domain.models.User;

public interface IUserServicePort {
    void createUser(User user);
}
