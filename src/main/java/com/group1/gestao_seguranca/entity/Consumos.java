package com.group1.gestao_seguranca.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "consumos")
public class Consumos extends Auditable {
    @Column(name = "valor_leitura", nullable = false)
    private int valorLeitura;

    @Column(name = "data_registo")
    private LocalDateTime dataRegisto;

    @Column(length = 255)
    private String observacoes;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "data_exclusao")
    private LocalDateTime dataExclusao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_consumo", nullable = false)
    private TipoConsumo tipoConsumo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_edificio", nullable = false)
    private Edificio edificio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    public Consumos() {
    }

    public Consumos(int valorLeitura, LocalDateTime dataRegisto, String observacoes, TipoConsumo tipoConsumo) {
        this.valorLeitura = valorLeitura;
        this.dataRegisto = dataRegisto;
        this.observacoes = observacoes;
        this.tipoConsumo = tipoConsumo;
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

    public int getValorLeitura() {
        return valorLeitura;
    }

    public void setValorLeitura(int valorLeitura) {
        this.valorLeitura = valorLeitura;
    }

    public LocalDateTime getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(LocalDateTime dataRegisto) {
        this.dataRegisto = dataRegisto;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public void setDataExclusao(LocalDateTime dataExclusao) {
        this.dataExclusao = dataExclusao;
    }

    public String getTipoConsumo() {
        return tipoConsumo.getTipoConsumo();
    }
}