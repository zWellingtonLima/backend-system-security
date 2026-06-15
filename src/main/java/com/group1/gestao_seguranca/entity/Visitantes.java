package com.group1.gestao_seguranca.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "visitantes")
public class Visitantes extends Auditable {

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 100)
    private String empresa;

    @Column(name = "doc_identificacao", unique = true, nullable = false, length = 30)
    private String documentoIdentificacao;

    private String observacoes;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "data_exclusao")
    private LocalDateTime dataExclusao;

    public Visitantes() {
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

    public void setNome(String nomeVisitante) {
        this.nome = nomeVisitante;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getDocumentoIdentificacao() {
        return documentoIdentificacao;
    }

    public void setDocumentoIdentificacao(String documentoIdentificacao) {
        this.documentoIdentificacao = documentoIdentificacao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}