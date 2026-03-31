package com.group1.gestao_seguranca.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "funcionarios")
@SQLDelete(sql = "UPDATE funcionarios SET ativo = false, data_exclusao = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("ativo = true")
public class Funcionarios extends Pessoa {

    @Column(name = "nome_funcionario", nullable = false, length = 150)
    private String nomeFuncionario;

    @Column(name = "numero_funcionario", nullable = false, unique = true)
    private String numeroFuncionario;

    @Column(nullable = false, length = 50)
    private String setor;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "data_exclusao")
    private LocalDateTime dataExclusao;

    @JsonIgnore
    @OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY)
    private List<Movimentacoes> movimentacoes;

    @JsonIgnore
    @OneToMany(mappedBy = "funcionarioComChave", fetch = FetchType.LAZY)
    private List<EntregaChaves> chavesRecebidas;

    @JsonIgnore
    @OneToMany(mappedBy = "funcionarioResponsavel", fetch = FetchType.LAZY)
    private List<Movimentacoes> movimentacoesComoResponsavel;

    // ==================== CONSTRUTORES ====================
    public Funcionarios(String nomeFuncionario, String setor) {
        this.nomeFuncionario = nomeFuncionario;
        this.setor = setor;
    }

    public Funcionarios() {
    }

    // ==================== GETTERS E SETTERS ====================
    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getNumeroFuncionario() {
        return numeroFuncionario;
    }

    public void setNumeroFuncionario(String numeroFuncionario) {
        this.numeroFuncionario = numeroFuncionario;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataExclusao() {
        return dataExclusao;
    }

    public void setDataExclusao(LocalDateTime dataExclusao) {
        this.dataExclusao = dataExclusao;
    }

    public List<Movimentacoes> getMovimentacoes() {
        return movimentacoes;
    }

    public void setMovimentacoes(List<Movimentacoes> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }

    public List<EntregaChaves> getChavesRecebidas() {
        return chavesRecebidas;
    }

    public void setChavesRecebidas(List<EntregaChaves> chavesRecebidas) {
        this.chavesRecebidas = chavesRecebidas;
    }

    public List<Movimentacoes> getMovimentacoesComoResponsavel() {
        return movimentacoesComoResponsavel;
    }

    public void setMovimentacoesComoResponsavel(List<Movimentacoes> movimentacoesComoResponsavel) {
        this.movimentacoesComoResponsavel = movimentacoesComoResponsavel;
    }
}
