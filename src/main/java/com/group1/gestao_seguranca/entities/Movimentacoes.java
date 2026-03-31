package com.group1.gestao_seguranca.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group1.gestao_seguranca.enums.TipoVisitanteEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "movimentacoes")
public class Movimentacoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimentacao")
    private int id;

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

    // Anulação
    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "motivo_anulacao")
    private String motivoAnulacao;

    @Column(name = "data_anulacao")
    private LocalDateTime dataAnulacao;

    @Column(name = "anulado_por")
    private String anuladoPor;

    // Auditoria
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

    // Getters e Setters existentes
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String v) {
        this.createUser = v;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime v) {
        this.createDate = v;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String v) {
        this.modifyUser = v;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime v) {
        this.modifyDate = v;
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