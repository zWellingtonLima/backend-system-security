package com.group1.gestao_seguranca.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ocorrencias")
public class Ocorrencias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ocorrencia")
    private int id;

    @Column(name = "hora_ocorrencia")
    private LocalDateTime horaOcorrencia;

    private String observacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private Users seguranca;

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

    public Ocorrencias() {
    }

    public Ocorrencias(LocalDateTime horaOcorrencia, String observacao, Users seguranca) {
        this.horaOcorrencia = horaOcorrencia;
        this.observacao = observacao;
        this.seguranca = seguranca;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Users getSeguranca() {
        return seguranca;
    }

    public void setSeguranca(Users seguranca) {
        this.seguranca = seguranca;
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
}
