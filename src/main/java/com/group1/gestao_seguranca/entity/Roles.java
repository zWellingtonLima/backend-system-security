package com.group1.gestao_seguranca.entity;

import com.group1.gestao_seguranca.enums.RoleEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleEnum role;

    public Roles() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role.toString();
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
