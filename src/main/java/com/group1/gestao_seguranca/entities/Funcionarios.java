package com.group1.gestao_seguranca.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "funcionarios")
public class Funcionarios extends Pessoa {

    @Column(name = "nome_funcionario", nullable = false, length = 150)
    private String nomeFuncionario;

    @Column(name = "numero_funcionario", nullable = false, unique = true)
    private String numeroFuncionario;

    @Column(nullable = false, length = 50)
    private String setor;

    @JsonIgnore
    @OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY)
    private List<Movimentacoes> movimentacoes;

    @JsonIgnore
    @OneToMany(mappedBy = "funcionarioComChave", fetch = FetchType.LAZY)
    private List<EntregaChaves> chavesRecebidas;

    @JsonIgnore
    @OneToMany(mappedBy = "funcionarioResponsavel", fetch = FetchType.LAZY)
    private List<Movimentacoes> movimentacoesComoResponsavel;

    public Funcionarios() {
    }

    public Funcionarios(String nomeFuncionario, String setor) {
        this.nomeFuncionario = nomeFuncionario;
        this.setor = setor;
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

    public String getNumeroFuncionario() {
        return numeroFuncionario;
    }

    public void setNumeroFuncionario(String numeroFuncionario) {
        this.numeroFuncionario = numeroFuncionario;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

}
