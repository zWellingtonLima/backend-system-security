package com.group1.gestao_seguranca.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer id;

    @Column(name="nome_seguranca",nullable = false, length = 150)
    private String nomeSeguranca;
    @Column(name="numero_seguranca", nullable = false, unique = true)
    private Integer numeroSeguranca;
    @Column(nullable = false, length = 30)
    private String password;

    @OneToMany(mappedBy = "seguranca", fetch = FetchType.LAZY)
    private List<Ocorrencias> rondas;
    @Column(name="create_user")
    private String createUser;
    @Column(name="create_date")
    private LocalDateTime createDate;
    @Column(name="modify_user")
    private String modifyUser;
    @Column(name="modify_date")
    private LocalDateTime modifyDate;

    @PrePersist
    protected void onCreate() {
        this.createDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifyDate = LocalDateTime.now();
    }

    public Users() {
    }

    public Users(String nomeSeguranca, int numeroSeguranca, String password) {
        this.nomeSeguranca = nomeSeguranca;
        this.numeroSeguranca = numeroSeguranca;
        this.password = password;
    }

    public List<Ocorrencias> getRondas() {
        return rondas;
    }

    public void setRondas(List<Ocorrencias> rondas) {
        this.rondas = rondas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeSeguranca() {
        return nomeSeguranca;
    }

    public void setNomeSeguranca(String nomeSeguranca) {
        this.nomeSeguranca = nomeSeguranca;
    }

    public int getNumeroSeguranca() {
        return numeroSeguranca;
    }

    public void setNumeroSeguranca(int numeroSeguranca) {
        this.numeroSeguranca = numeroSeguranca;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
