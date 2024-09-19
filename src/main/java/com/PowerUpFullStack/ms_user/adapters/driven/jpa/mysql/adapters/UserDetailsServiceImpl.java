package com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.adapters;

import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.entities.PrincipalUser;
import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.entities.RoleEntity;
import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.entities.UserEntity;
import com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    IUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

        if(user.getRoleId() == null) {
            throw new UsernameNotFoundException("User Not Found with email: " + email);
        }

        List<RoleEntity> roles = new ArrayList<>();
        roles.add(user.getRoleId());

        return PrincipalUser.build(user, roles);
    }
}
