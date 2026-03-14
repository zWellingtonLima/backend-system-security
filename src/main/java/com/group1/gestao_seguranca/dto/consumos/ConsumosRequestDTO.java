package com.group1.gestao_seguranca.dto.consumos;

import com.group1.gestao_seguranca.enums.TipoConsumoEnum;

public class ConsumosRequestDTO {
    private Integer valorLeitura;
    private String observacao;
    private TipoConsumoEnum tipoConsumo;
    private String createUser;

    public ConsumosRequestDTO() {
    }

    public ConsumosRequestDTO(Integer valorLeitura, String observacao, TipoConsumoEnum tipoConsumo, String createUser) {
        this.valorLeitura = valorLeitura;
        this.observacao = observacao;
        this.tipoConsumo = tipoConsumo;
        this.createUser = createUser;
    }

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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
}
