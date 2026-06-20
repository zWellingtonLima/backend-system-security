package com.group1.gestao_seguranca.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacoes")
public class Movimentacoes extends Auditable {

    @Column(name = "hora_entrada")
    private LocalDateTime horaEntrada;

    @Column(name = "hora_saida")
    private LocalDateTime horaSaida;

    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcionario")
    private Funcionarios funcionario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_visita")
    private Visitas visita;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "motivo_anulacao")
    private String motivoAnulacao; // TODO: verificar possibilidade de obrigar a insercao de um motivo

    @Column(name = "data_anulacao", nullable = false)
    private LocalDateTime dataAnulacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_anulado_por", nullable = false)
    private User anuladoPor; // Seguranca que anulou a movimentacao

    public Movimentacoes() {
    }

    public User getAnuladoPor() {
        return anuladoPor;
    }

    public void setAnuladoPor(User anuladoPor) {
        this.anuladoPor = anuladoPor;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public LocalDateTime getDataAnulacao() {
        return dataAnulacao;
    }

    public void anularMovimentacao() {
        this.ativo = false;
        this.dataAnulacao = LocalDateTime.now();
    }

    public void reativarMovimentacao() {
        this.ativo = true;
        this.dataAnulacao = null;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalDateTime v) {
        this.horaEntrada = v;
    }

    public LocalDateTime getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(LocalDateTime v) {
        this.horaSaida = v;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String v) {
        this.observacoes = v;
    }

    public Funcionarios getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionarios v) {
        this.funcionario = v;
    }

    public String getMotivoAnulacao() {
        return motivoAnulacao;
    }

    public void setMotivoAnulacao(String motivoAnulacao) {
        this.motivoAnulacao = motivoAnulacao;
    }
}