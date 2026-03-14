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

    @Column(name = "hora_entrega")
    private LocalDateTime horaEntrega;

    @Column(name = "hora_devolucao")
    private LocalDateTime horaDevolucao;

    @Column(name = "devolvida_por", length = 50)
    private String devolvidaPor;

    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chave")
    private Chaves chave;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_molho")
    private Molhos molho;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_movimentacao", nullable = false)
    private Movimentacoes movimentacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcionario")
    private Funcionarios funcionarioComChave;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_visita")
    private Visitas visitaComChave;

    @Column(name = "create_user")
    private String createUser;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "modify_user")
    private String modifyUser;
    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

    @PrePersist
    protected void onCreate() {
        this.createDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifyDate = LocalDateTime.now();
    }

    public EntregaChaves() {
    }

    public EntregaChaves(LocalDateTime horaEntrega, Chaves chave) {
        this.horaEntrega = horaEntrega;
        this.chave = chave;
    }

    public String getDevolvidaPor() {
        return devolvidaPor;
    }

    public void setDevolvidaPor(String devolvidaPor) {
        this.devolvidaPor = devolvidaPor;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Molhos getMolho() {
        return molho;
    }

    public void setMolho(Molhos molho) {
        this.molho = molho;
    }

    public Movimentacoes getMovimentacao() {
        return movimentacao;
    }

    public void setMovimentacao(Movimentacoes movimentacao) {
        this.movimentacao = movimentacao;
    }

    public Funcionarios getFuncionarioComChave() {
        return funcionarioComChave;
    }

    public void setFuncionarioComChave(Funcionarios funcionarioComChave) {
        this.funcionarioComChave = funcionarioComChave;
    }

    public Visitas getVisitaComChave() {
        return visitaComChave;
    }

    public void setVisitaComChave(Visitas visitaComChave) {
        this.visitaComChave = visitaComChave;
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
