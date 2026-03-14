package com.group1.gestao_seguranca.dto.consumos;

import com.group1.gestao_seguranca.entities.Consumos;

import java.time.LocalDateTime;

public class ConsumosResponseDTO {
    private Integer id;
    private Integer valorLeitura;
    private LocalDateTime dataRegisto;
    private String observacao;
    private String tipoConsumo;
    private Integer consumoCalculado;
    private String createUser;
    private LocalDateTime createDate;

    // A conversão vive aqui — o Service só chama ConsumosResponseDTO.from(consumo, calculado)
    public static ConsumosResponseDTO from(Consumos consumo, Integer consumoCalculado) {
        ConsumosResponseDTO dto = new ConsumosResponseDTO();
        dto.id = consumo.getId();
        dto.valorLeitura = consumo.getValorLeitura();
        dto.dataRegisto = consumo.getDataRegisto();
        dto.observacao = consumo.getObservacao();
        dto.tipoConsumo = consumo.getTipoConsumo().getLabel();
        dto.consumoCalculado = consumoCalculado;
        dto.createUser = consumo.getCreateUser();
        dto.createDate = consumo.getCreateDate();
        return dto;
    }

    // Overload para quando não há consumo calculado (listagens simples)
    public static ConsumosResponseDTO from(Consumos consumo) {
        return from(consumo, null);
    }

    public Integer getId() {
        return id;
    }

    public Integer getValorLeitura() {
        return valorLeitura;
    }

    public LocalDateTime getDataRegisto() {
        return dataRegisto;
    }

    public String getObservacao() {
        return observacao;
    }

    public String getTipoConsumo() {
        return tipoConsumo;
    }

    public Integer getConsumoCalculado() {
        return consumoCalculado;
    }

    public String getCreateUser() {
        return createUser;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }
}