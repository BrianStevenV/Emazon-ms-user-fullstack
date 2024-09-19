package com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.entities.utils.EntityConstant.ROLE_COLUMN_DESCRIPTION;
import static com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.entities.utils.EntityConstant.ROLE_COLUMN_NAME;
import static com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.entities.utils.EntityConstant.ROLE_ENTITY_NAME;
import static com.PowerUpFullStack.ms_user.adapters.driven.jpa.mysql.entities.utils.EntityConstant.ROLE_ONE_TO_MANY_MAPPED_BY;

@Entity
@Table(name = ROLE_ENTITY_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = ROLE_COLUMN_NAME ,unique = true, nullable = false)
    private String name;
    @Column(name = ROLE_COLUMN_DESCRIPTION, unique = true, nullable = false)
    private String description;
    @OneToMany(mappedBy = ROLE_ONE_TO_MANY_MAPPED_BY)
    private List<UserEntity> users;
}
