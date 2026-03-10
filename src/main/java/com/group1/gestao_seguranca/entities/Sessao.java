package com.group1.gestao_seguranca.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sessao")
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sessao")
    private int id;

    @Column(nullable = false)
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSaida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private Users user;

    private String createUser;
    private LocalDateTime createDate;
    private String modifyUser;
    private LocalDateTime modifyDate;

    public Sessao() {
    }

    public Sessao(LocalDateTime horaEntrada, LocalDateTime horaSaida, Users user) {
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalDateTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public LocalDateTime getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(LocalDateTime horaSaida) {
        this.horaSaida = horaSaida;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
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
