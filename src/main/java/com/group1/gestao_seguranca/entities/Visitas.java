package com.group1.gestao_seguranca.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "visitas")
public class Visitas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chave")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVisita tipoVisita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_visitante", nullable = false)
    private Visitantes visitante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcionario")
    private Funcionarios funcionarioResponsavel;

    private String observacoes;

    private String createUser;
    private LocalDateTime createDate;
    private String modifyUser;
    private LocalDateTime modifyDate;

    @OneToMany(mappedBy = "visita", fetch = FetchType.LAZY)
    private List<Movimentacoes> visitaMovimentacoes;

    @OneToMany(mappedBy = "visitaComChave", fetch = FetchType.LAZY)
    private List<EntregaChaves> chavesRecebidasVisita;

    public Visitas() {
    }

    public Visitas(TipoVisita tipoVisita, Visitantes visitante, Funcionarios funcionarioResponsavel, String observacoes) {
        this.tipoVisita = tipoVisita;
        this.visitante = visitante;
        this.funcionarioResponsavel = funcionarioResponsavel;
        this.observacoes = observacoes;
    }

    public Visitas(TipoVisita tipoVisita, Visitantes visitante, String observacoes) {
        this.tipoVisita = tipoVisita;
        this.visitante = visitante;
        this.observacoes = observacoes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoVisita getTipoVisita() {
        return tipoVisita;
    }

    public void setTipoVisita(TipoVisita tipoVisita) {
        this.tipoVisita = tipoVisita;
    }

    public Visitantes getVisitante() {
        return visitante;
    }

    public void setVisitante(Visitantes visitante) {
        this.visitante = visitante;
    }

    public Funcionarios getFuncionarioResponsavel() {
        return funcionarioResponsavel;
    }

    public void setFuncionarioResponsavel(Funcionarios funcionarioResponsavel) {
        this.funcionarioResponsavel = funcionarioResponsavel;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
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

enum TipoVisita {
    VISITA, ENTREGA, SERVICO, MANUTENCAO, REUNIAO
}
