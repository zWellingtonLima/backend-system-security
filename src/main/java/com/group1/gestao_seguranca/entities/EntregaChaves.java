package com.group1.gestao_seguranca.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "entrega_chaves")
public class EntregaChaves {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entrega")
    private int id;

    private LocalDateTime horaEntrega;
    private LocalDateTime horaDevolucao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chave", nullable = false)
    private Chaves chave;

    @Column(name="create_user")
    private String createUser;
    @Column(name="create_date")
    private LocalDateTime createDate;
    @Column(name="modify_user")
    private String modifyUser;
    @Column(name="modify_date")
    private LocalDateTime modifyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcionario")
    private Funcionarios funcionarioComChave;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_visita")
    private Visitas visitaComChave;

    public EntregaChaves() {
    }

    public EntregaChaves(LocalDateTime horaEntrega, Chaves chave) {
        this.horaEntrega = horaEntrega;
        this.chave = chave;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getHoraEntrega() {
        return horaEntrega;
    }

    public void setHoraEntrega(LocalDateTime horaEntrega) {
        this.horaEntrega = horaEntrega;
    }

    public LocalDateTime getHoraDevolucao() {
        return horaDevolucao;
    }

    public void setHoraDevolucao(LocalDateTime horaDevolucao) {
        this.horaDevolucao = horaDevolucao;
    }

    public Chaves getChave() {
        return chave;
    }

    public void setChave(Chaves chave) {
        this.chave = chave;
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
