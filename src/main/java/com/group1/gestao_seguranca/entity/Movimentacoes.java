package com.group1.gestao_seguranca.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group1.gestao_seguranca.enums.TipoVisitanteEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

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
    @JoinColumn(name = "id_visitante")
    private Visitantes visitante; // TODO: criar tabela Visita

    @JsonIgnore
    @OneToMany(mappedBy = "movimentacao", fetch = FetchType.LAZY)
    private List<EntregaChaves> entregas;

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

    public Movimentacoes(LocalDateTime horaEntrada, String observacoes, Visitantes visitante) {
        this.horaEntrada = horaEntrada;
        this.observacoes = observacoes;
        this.visitante = visitante;
    }

    public Movimentacoes(LocalDateTime horaEntrada, String observacoes, Funcionarios funcionario) {
        this.horaEntrada = horaEntrada;
        this.observacoes = observacoes;
        this.funcionario = funcionario;
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

    public Visitantes getVisitante() {
        return visitante;
    }

    public void setVisitante(Visitantes v) {
        this.visitante = v;
    }

    public List<EntregaChaves> getEntregas() {
        return entregas;
    }

    public void setEntregas(List<EntregaChaves> v) {
        this.entregas = v;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getMotivoAnulacao() {
        return motivoAnulacao;
    }

    public void setMotivoAnulacao(String motivoAnulacao) {
        this.motivoAnulacao = motivoAnulacao;
    }

    public LocalDateTime getDataAnulacao() {
        return dataAnulacao;
    }

    public void setDataAnulacao(LocalDateTime dataAnulacao) {
        this.dataAnulacao = dataAnulacao;
    }
}