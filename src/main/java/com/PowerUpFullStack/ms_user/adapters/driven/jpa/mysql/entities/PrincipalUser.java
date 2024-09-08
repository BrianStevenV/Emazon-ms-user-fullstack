package com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PrincipalUser implements UserDetails {
    private String email;
    private String password;
    private Long id;

    private Collection<? extends GrantedAuthority> authorities;

    public PrincipalUser(String email, String password, Long id, Collection<? extends GrantedAuthority> authorities) {
        this.email = email;
        this.password = password;
        this.id = id;
        this.authorities = authorities;
    }

    public static PrincipalUser build(UserEntity user, List<RoleEntity> roles) {
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new PrincipalUser(
                user.getEmail(),
                user.getPassword(),
                user.getId(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public Long getId() {
        return id;
    }
}
