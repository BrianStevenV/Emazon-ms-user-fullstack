package com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.entities.utils.EntityConstant.USER_COLUMN_BIRTHDATE;
import static com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.entities.utils.EntityConstant.USER_COLUMN_DNI;
import static com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.entities.utils.EntityConstant.USER_COLUMN_EMAIL;
import static com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.entities.utils.EntityConstant.USER_COLUMN_NAME;
import static com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.entities.utils.EntityConstant.USER_COLUMN_PASSWORD;
import static com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.entities.utils.EntityConstant.USER_COLUMN_PHONE;
import static com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.entities.utils.EntityConstant.USER_COLUMN_ROLE_ID;
import static com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.entities.utils.EntityConstant.USER_COLUMN_SURNAME;
import static com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.entities.utils.EntityConstant.USER_ENTITY_NAME;

@Entity
@Table(name = USER_ENTITY_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = USER_COLUMN_NAME, nullable = false)
    private String name;
    @Column(name = USER_COLUMN_SURNAME, nullable = false)
    private String surName;
    @Column(name = USER_COLUMN_DNI, unique = true, nullable = false)
    private String dni;
    @Column(name = USER_COLUMN_PHONE, unique = true, nullable = false)
    private String phone;
    @Column(name = USER_COLUMN_BIRTHDATE, nullable = false)
    private LocalDate birthDate;
    @Column(name = USER_COLUMN_EMAIL, unique = true, nullable = false)
    private String email;
    @Column(name = USER_COLUMN_PASSWORD, nullable = false)
    private String password;
    @ManyToOne
    @JoinColumn(name = USER_COLUMN_ROLE_ID)
    private RoleEntity roleId;
}
