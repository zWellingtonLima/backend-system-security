package com.group1.gestao_seguranca.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacoes")
public class Movimentacoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimentacao")
    private int id;

    private LocalDateTime horaEntrada;
    private LocalDateTime horaSaida;
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcionario")
    private Funcionarios funcionario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_visita")
    private Visitas visita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_entrega")
    private EntregaChaves entregaChave;

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

    public Movimentacoes() {
    }

    public Movimentacoes(LocalDateTime horaEntrada, LocalDateTime horaSaida, String observacoes, Funcionarios funcionario) {
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
        this.observacoes = observacoes;
        this.funcionario = funcionario;
    }

    public Movimentacoes(LocalDateTime horaEntrada, LocalDateTime horaSaida, String observacoes, Funcionarios funcionario, EntregaChaves entregaChave) {
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
        this.observacoes = observacoes;
        this.funcionario = funcionario;
        this.entregaChave = entregaChave;
    }

    public Movimentacoes(LocalDateTime horaEntrada, LocalDateTime horaSaida, String observacoes, Visitas visita) {
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
        this.observacoes = observacoes;
        this.visita = visita;
    }

    public Movimentacoes(LocalDateTime horaEntrada, LocalDateTime horaSaida, String observacoes, Visitas visita, EntregaChaves entregaChave) {
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
        this.observacoes = observacoes;
        this.visita = visita;
        this.entregaChave = entregaChave;
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

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Funcionarios getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionarios funcionario) {
        this.funcionario = funcionario;
    }

    public Visitas getVisita() {
        return visita;
    }

    public void setVisita(Visitas visita) {
        this.visita = visita;
    }

    public EntregaChaves getEntregaChave() {
        return entregaChave;
    }

    public void setEntregaChave(EntregaChaves entregaChave) {
        this.entregaChave = entregaChave;
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
