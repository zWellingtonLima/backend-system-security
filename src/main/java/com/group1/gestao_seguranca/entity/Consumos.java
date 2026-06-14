package com.group1.gestao_seguranca.entity;

import com.group1.gestao_seguranca.enums.TipoConsumoEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "consumos")
public class Consumos extends Auditable {
    @Column(name = "valor_leitura", nullable = false)
    private int valorLeitura;

    @Column(name = "data_registo")
    private LocalDateTime dataRegisto;

    private String observacao;

    @Column(nullable = false)
    private boolean ativo = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_consumo", nullable = false)
    private TipoConsumo tipoConsumo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    public Consumos() {
    }

    public Consumos(int valorLeitura, LocalDateTime dataRegisto, String observacao, TipoConsumo tipoConsumo) {
        this.valorLeitura = valorLeitura;
        this.dataRegisto = dataRegisto;
        this.observacao = observacao;
        this.tipoConsumo = tipoConsumo;
    }

    public User getUser() {
        return user;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
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
        return observacao;
    }

    public void setObservacoes(String observacoes) {
        this.observacao = observacao;
    }

    public TipoConsumo getTipoConsumo() {
        return tipoConsumo;
    }

    public TipoConsumoEnum getTipoConsumoEnum() {
        return tipoConsumo.getTipoConsumo();
    }

    public void setTipoConsumo(TipoConsumo tipoConsumo) {
        this.tipoConsumo = tipoConsumo;
    }
}