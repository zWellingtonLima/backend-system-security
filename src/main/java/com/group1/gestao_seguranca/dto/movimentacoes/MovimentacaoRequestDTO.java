package com.group1.gestao_seguranca.dto.movimentacoes;

import com.group1.gestao_seguranca.enums.TipoEntrada;
import com.group1.gestao_seguranca.enums.TipoVisitanteEnum;
import jakarta.validation.constraints.NotNull;

public class MovimentacaoRequestDTO {
    @NotNull(message = "Tipo de entrada é obrigatório")
    private TipoEntrada tipoEntrada;

    private Integer idFuncionario;
    private Integer idVisitante;

    private TipoVisitanteEnum tipoVisita;

    private Integer idFuncionarioResponsavel;

    private String setorDestino;

    private String observacoes;

    private EntregaChaveDTO entregaChave;

    public TipoVisitanteEnum getTipoVisita() {
        return tipoVisita;
    }

    public void setTipoVisita(TipoVisitanteEnum tipoVisita) {
        this.tipoVisita = tipoVisita;
    }

    public Integer getIdFuncionarioResponsavel() {
        return idFuncionarioResponsavel;
    }

    public void setIdFuncionarioResponsavel(Integer idFuncionarioResponsavel) {
        this.idFuncionarioResponsavel = idFuncionarioResponsavel;
    }

    public String getSetorDestino() {
        return setorDestino;
    }

    public void setSetorDestino(String setorDestino) {
        this.setorDestino = setorDestino;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public EntregaChaveDTO getEntregaChave() {
        return entregaChave;
    }

    public void setEntregaChave(EntregaChaveDTO entregaChave) {
        this.entregaChave = entregaChave;
    }

    public TipoEntrada getTipoEntrada() {
        return tipoEntrada;
    }

    public void setTipoEntrada(TipoEntrada tipoEntrada) {
        this.tipoEntrada = tipoEntrada;
    }

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public Integer getIdVisitante() {
        return idVisitante;
    }

    public void setIdVisitante(Integer idVisitante) {
        this.idVisitante = idVisitante;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacao(String observacoes) {
        this.observacoes = observacoes;
    }
}
