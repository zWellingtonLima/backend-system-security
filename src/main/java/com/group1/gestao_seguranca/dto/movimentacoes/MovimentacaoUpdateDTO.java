package com.group1.gestao_seguranca.dto.movimentacoes;

import com.group1.gestao_seguranca.enums.TipoVisitanteEnum;

/**
 * Todos os campos são opcionais (PATCH semântico).
 * O serviço aplica cada campo apenas se vier não-nulo.
 * <p>
 * Regras:
 * - Se a movimentação já tiver saída registada → só "observacoes" é aceite.
 * - Não é permitido trocar o tipo de pessoa (funcionário ↔ visitante).
 * - Ao trocar de funcionário/visitante, valida-se que a nova pessoa
 * não tem outra entrada ativa.
 */
public class MovimentacaoUpdateDTO {

    // Editável sempre (mesmo após saída)
    private String observacoes;

    // Editável apenas enquanto a entrada estiver ativa (sem saída)
    private String setorDestino;
    private Integer idFuncionario;
    private Integer idVisitante;
    private TipoVisitanteEnum tipoVisita;
    private Integer idFuncionarioResponsavel;

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String v) {
        this.observacoes = v;
    }

    public String getSetorDestino() {
        return setorDestino;
    }

    public void setSetorDestino(String v) {
        this.setorDestino = v;
    }

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer v) {
        this.idFuncionario = v;
    }

    public Integer getIdVisitante() {
        return idVisitante;
    }

    public void setIdVisitante(Integer v) {
        this.idVisitante = v;
    }

    public TipoVisitanteEnum getTipoVisita() {
        return tipoVisita;
    }

    public void setTipoVisita(TipoVisitanteEnum v) {
        this.tipoVisita = v;
    }

    public Integer getIdFuncionarioResponsavel() {
        return idFuncionarioResponsavel;
    }

    public void setIdFuncionarioResponsavel(Integer v) {
        this.idFuncionarioResponsavel = v;
    }
}