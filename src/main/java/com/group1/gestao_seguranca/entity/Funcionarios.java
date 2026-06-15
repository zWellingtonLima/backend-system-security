package com.group1.gestao_seguranca.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "funcionarios")
public class Funcionarios extends Auditable {
    @Column(nullable = false, length = 150)
    private String nome;

    @Column(name = "numero_funcionario", length = 30, nullable = false, unique = true)
    private String numeroFuncionario;

    @Column(nullable = false, length = 50)
    private String setor; // TODO: Verificar se existe uma tabela de lookup para linkar

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "data_exclusao")
    private LocalDateTime dataExclusao;

    public Funcionarios() {
    }

    public boolean isAtivo() {
        return ativo;
    }

    public LocalDateTime getDataExclusao() {
        return dataExclusao;
    }

    public void desativar() {
        this.ativo = false;
        this.dataExclusao = LocalDateTime.now();
    }

    public void reativar() {
        this.ativo = true;
        this.dataExclusao = null;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nomeFuncionario) {
        this.nome = nomeFuncionario;
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

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public void setDataExclusao(LocalDateTime dataExclusao) {
        this.dataExclusao = dataExclusao;
    }
}
