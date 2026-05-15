package com.group1.gestao_seguranca.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "entrega_chaves")
public class EntregaChaves extends Auditable {

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
    @JoinColumn(name = "id_movimentacao", nullable = false)
    private Movimentacoes movimentacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcionario")
    private Funcionarios funcionarioComChave;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_visitante")
    private Visitantes visitanteComChave;

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

    public Visitantes getVisitanteComChave() {
        return visitanteComChave;
    }

    public void setVisitanteComChave(Visitantes visitanteComChave) {
        this.visitanteComChave = visitanteComChave;
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

}
