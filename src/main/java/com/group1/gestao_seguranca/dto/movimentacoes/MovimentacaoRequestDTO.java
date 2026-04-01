package com.group1.gestao_seguranca.dto.movimentacoes;

import com.group1.gestao_seguranca.dto.chaves.EntregaChaveDTO;
import com.group1.gestao_seguranca.enums.TipoVisitanteEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

import java.util.List;

public class MovimentacaoRequestDTO {

    private Integer idFuncionario;
    private Integer idVisitante;
    private TipoVisitanteEnum tipoVisita;
    private Integer idFuncionarioResponsavel;
    private String setorDestino;
    private String observacoes;
    private String tipoEntrada;

    @Valid
    @Size(max = 10, message = "Não é possível associar mais de 10 chaves a uma entrada.")
    private List<EntregaChaveDTO> entregasChave;

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

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getTipoEntrada() {
        return tipoEntrada;
    }

    public void setTipoEntrada(String tipoEntrada) {
        this.tipoEntrada = tipoEntrada;
    }

    public List<EntregaChaveDTO> getEntregasChave() {
        return entregasChave;
    }

    public void setEntregasChave(List<EntregaChaveDTO> entregasChave) {
        this.entregasChave = entregasChave;
    }
}