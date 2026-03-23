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

    public TipoVisitanteEnum getTipoVisitante() {
        return tipoVisitante;
    }

    public void setTipoVisitante(TipoVisitanteEnum tipoVisitante) {
        this.tipoVisitante = tipoVisitante;
    }

    public Funcionarios getFuncionarioResponsavel() {
        return funcionarioResponsavel;
    }

    public void setFuncionarioResponsavel(Funcionarios funcionarioResponsavel) {
        this.funcionarioResponsavel = funcionarioResponsavel;
    }

    public String getSetorDestino() {
        return setorDestino;
    }

    public void setSetorDestino(String setorDestino) {
        this.setorDestino = setorDestino;
    }

    public List<EntregaChaves> getEntregas() {
        return entregas;
    }

    public void setEntregas(List<EntregaChaves> entregas) {
        this.entregas = entregas;
    }

    public Visitantes getVisitante() {
        return visitante;
    }

    public void setVisitante(Visitantes visitante) {
        this.visitante = visitante;
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
