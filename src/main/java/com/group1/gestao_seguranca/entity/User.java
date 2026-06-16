package com.group1.gestao_seguranca.entity;

import jakarta.persistence.*;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends Auditable implements UserDetails {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_role", nullable = false)
    private Roles role;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(name = "numero_identificacao", nullable = false, unique = true)
    private String numeroIdentificacao; // TODO: verificar que tipo de identificação os seguranças possuem

    @Column(nullable = false)
    private String password;

    public User() {
    }

    public User(String nome, String numeroIdentificacao, String password) {
        this.nome = nome;
        this.numeroIdentificacao = numeroIdentificacao;
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role.toString();
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public String getNumeroIdentificacao() {
        return numeroIdentificacao;
    }

    public void setNumeroIdentificacao(String numeroIdentificacao) {
        this.numeroIdentificacao = numeroIdentificacao;
    }

    // --------- Metodos da Interface UserDetails do Spring Security -------------
    @Override
    @NonNull
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    @NonNull
    public String getUsername() { // Identificador de Autenticacao
        return this.numeroIdentificacao;
    }
}
