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

    @Column(name = "setor_destino", length = 30)
    private String setorDestino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcionario")
    private Funcionarios funcionario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_visitante")
    private Visitantes visitante;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_visitante")
    private TipoVisitanteEnum tipoVisitante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcionario_responsavel")
    private Funcionarios funcionarioResponsavel;

    @JsonIgnore
    @OneToMany(mappedBy = "movimentacao", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<EntregaChaves> entregas;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "motivo_anulacao")
    private String motivoAnulacao;

    @Column(name = "data_anulacao")
    private LocalDateTime dataAnulacao;

    @Column(name = "anulado_por")
    private String anuladoPor;

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

    public String getSetorDestino() {
        return setorDestino;
    }

    public void setSetorDestino(String v) {
        this.setorDestino = v;
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

    public TipoVisitanteEnum getTipoVisitante() {
        return tipoVisitante;
    }

    public void setTipoVisitante(TipoVisitanteEnum v) {
        this.tipoVisitante = v;
    }

    public Funcionarios getFuncionarioResponsavel() {
        return funcionarioResponsavel;
    }

    public void setFuncionarioResponsavel(Funcionarios v) {
        this.funcionarioResponsavel = v;
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

    public String getAnuladoPor() {
        return anuladoPor;
    }

    public void setAnuladoPor(String anuladoPor) {
        this.anuladoPor = anuladoPor;
    }
}