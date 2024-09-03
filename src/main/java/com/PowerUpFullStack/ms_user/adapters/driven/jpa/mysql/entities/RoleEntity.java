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

@Entity
@Table(name = "role")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name" ,unique = true, nullable = false)
    private String name;
    @Column(name = "description", unique = true, nullable = false)
    private String description;
    @OneToMany(mappedBy = "roleId")
    private List<UserEntity> users;
}
