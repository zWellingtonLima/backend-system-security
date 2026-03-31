package com.group1.gestao_seguranca.dto.consumos;

import com.group1.gestao_seguranca.enums.TipoConsumoEnum;

import java.time.LocalDateTime;

public class ConsumosRequestDTO {

    private Integer valorLeitura;
    private String observacao;
    private TipoConsumoEnum tipoConsumo;
    private LocalDateTime dataRegisto;   // ← Campo necessário para o update

    // ==================== CONSTRUTORES ====================
    public ConsumosRequestDTO() {
    }

    public ConsumosRequestDTO(Integer valorLeitura, String observacao, TipoConsumoEnum tipoConsumo) {
        this.valorLeitura = valorLeitura;
        this.observacao = observacao;
        this.tipoConsumo = tipoConsumo;
    }

    public ConsumosRequestDTO(Integer valorLeitura, String observacao, TipoConsumoEnum tipoConsumo, LocalDateTime dataRegisto) {
        this.valorLeitura = valorLeitura;
        this.observacao = observacao;
        this.tipoConsumo = tipoConsumo;
        this.dataRegisto = dataRegisto;
    }

    // ==================== GETTERS E SETTERS ====================
    public Integer getValorLeitura() {
        return valorLeitura;
    }

    public void setValorLeitura(Integer valorLeitura) {
        this.valorLeitura = valorLeitura;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public TipoConsumoEnum getTipoConsumo() {
        return tipoConsumo;
    }

    public void setTipoConsumo(TipoConsumoEnum tipoConsumo) {
        this.tipoConsumo = tipoConsumo;
    }

    public LocalDateTime getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(LocalDateTime dataRegisto) {
        this.dataRegisto = dataRegisto;
    }
}