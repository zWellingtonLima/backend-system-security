package com.group1.gestao_seguranca.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "visitantes")
@SQLDelete(sql = "UPDATE visitantes SET ativo = false, data_exclusao = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("ativo = true")
public class Visitantes extends Pessoa {

    @Column(name = "nome_visitante", nullable = false, length = 150)
    private String nomeVisitante;

    @Column(name = "empresa", length = 100)
    private String empresa;

    @Column(name = "documento_identificacao", unique = true, nullable = false, length = 30)
    private String documentoIdentificacao;

    private String observacoes;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "data_exclusao")
    private LocalDateTime dataExclusao;

    @OneToMany(mappedBy = "visitante", fetch = FetchType.LAZY)
    private List<Movimentacoes> movimentacoes;

    @OneToMany(mappedBy = "visitanteComChave", fetch = FetchType.LAZY)
    private List<EntregaChaves> chavesRecebidas;

    // ==================== CONSTRUTORES ====================
    public Visitantes() {
    }

    public Visitantes(String nomeVisitante, String documentoIdentificacao) {
        this.nomeVisitante = nomeVisitante;
        this.documentoIdentificacao = documentoIdentificacao;
    }

    public Visitantes(String nomeVisitante, String empresa, String documentoIdentificacao) {
        this.nomeVisitante = nomeVisitante;
        this.empresa = empresa;
        this.documentoIdentificacao = documentoIdentificacao;
    }

    // ==================== GETTERS E SETTERS ====================
    public String getNomeVisitante() {
        return nomeVisitante;
    }

    public void setNomeVisitante(String nomeVisitante) {
        this.nomeVisitante = nomeVisitante;
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
}