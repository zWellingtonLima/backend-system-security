package com.group1.gestao_seguranca.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "rondas")
public class Rondas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ronda")
    private int id;

    private LocalDateTime horaRonda;

    private String ocorrencias;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private Users seguranca;

    private String createUser;
    private LocalDateTime createDate;
    private String modifyUser;
    private LocalDateTime modifyDate;

    public Rondas() {
    }

    public Rondas(LocalDateTime horaRonda, String ocorrencias, Users seguranca) {
        this.horaRonda = horaRonda;
        this.ocorrencias = ocorrencias;
        this.seguranca = seguranca;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getHoraRonda() {
        return horaRonda;
    }

    public void setHoraRonda(LocalDateTime horaRonda) {
        this.horaRonda = horaRonda;
    }

    public String getOcorrencias() {
        return ocorrencias;
    }

    public void setOcorrencias(String ocorrencias) {
        this.ocorrencias = ocorrencias;
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
