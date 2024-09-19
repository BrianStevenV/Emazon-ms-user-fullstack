package com.PowerUpFullStack.ms_user.domain.models;

import java.time.LocalDate;

public class User {
    private Long id;
    private String name;
    private String surName;
    private String dni;
    private String phone;
    private LocalDate birthDate;
    private String email;
    private String password;
    private Role roleId;

    public User(Long id, String name, String surName, String dni, String phone, LocalDate birthDate, String email, String password) {
        this.id = id;
        this.name = name;
        this.surName = surName;
        this.dni = dni;
        this.phone = phone;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
    }

    public User(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRoleId() {
        return roleId;
    }

    public void setRoleId(Role roleId) {
        this.roleId = roleId;
    }
}
