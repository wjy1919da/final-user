package com.cmall.userservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    @Getter
    @Setter
    private int roleId;

    @Getter
    @Setter
    @Column(name = "role_name")
    private String roleName;

    @Getter
    @Setter
    @Column(name = "description")
    private String description;
}
