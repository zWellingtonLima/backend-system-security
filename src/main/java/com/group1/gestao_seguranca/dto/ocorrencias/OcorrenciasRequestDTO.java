package com.group1.gestao_seguranca.dto.ocorrencias;

import com.group1.gestao_seguranca.enums.EstadoOcorrenciaEnum;
import com.group1.gestao_seguranca.enums.TipoOcorrenciaEnum;

public class OcorrenciasRequestDTO {

    private TipoOcorrenciaEnum tipoOcorrencia;
    private String ocorrencia;
    private EstadoOcorrenciaEnum estado;

    // ==================== GETTERS E SETTERS ====================

    public TipoOcorrenciaEnum getTipoOcorrencia() {
        return tipoOcorrencia;
    }

    public void setTipoOcorrencia(TipoOcorrenciaEnum tipoOcorrencia) {
        this.tipoOcorrencia = tipoOcorrencia;
    }

    public String getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(String ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    public EstadoOcorrenciaEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoOcorrenciaEnum estado) {
        this.estado = estado;
    }
}