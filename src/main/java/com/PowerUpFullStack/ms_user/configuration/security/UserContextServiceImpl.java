package com.PowerUpFullStack.ms_user.configuration.security;

import com.PowerUpFullStack.ms_user.configuration.security.utils.SecurityUtils;
import org.springframework.stereotype.Service;

@Service
public class UserContextServiceImpl implements IUserContextService{

    @Override
    public String getAuthenticationUserRole() {
        return SecurityUtils.getRolesFromSecurityContext();
    }

}
