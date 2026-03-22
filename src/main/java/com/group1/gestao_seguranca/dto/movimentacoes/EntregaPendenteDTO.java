package com.group1.gestao_seguranca.dto.movimentacoes;

import com.group1.gestao_seguranca.enums.TipoChaveEnum;

public class EntregaPendenteDTO {

    private int idEntrega;
    private String descricao;   // ex: "CHV-101-P" ou "Molho TI"
    private TipoChaveEnum tipo;        // "CHAVE" ou "MOLHO"
    private String observacoes;

    public EntregaPendenteDTO(int idEntrega, String descricao, TipoChaveEnum tipo, String observacoes) {
        this.idEntrega = idEntrega;
        this.descricao = descricao;
        this.tipo = tipo;
        this.observacoes = observacoes;
    }

    // getters e setters
    public int getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(int idEntrega) {
        this.idEntrega = idEntrega;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoChaveEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoChaveEnum tipo) {
        this.tipo = tipo;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
