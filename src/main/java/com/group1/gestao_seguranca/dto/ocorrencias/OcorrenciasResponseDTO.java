package com.group1.gestao_seguranca.dto.ocorrencias;

import com.group1.gestao_seguranca.entities.EstadoOcorrencia;
import com.group1.gestao_seguranca.entities.Ocorrencias;
import com.group1.gestao_seguranca.entities.TipoOcorrencia;

import java.time.LocalDateTime;

public class OcorrenciasResponseDTO {

    private Integer id;
    private LocalDateTime createDate;
    private String createUser;
    private TipoOcorrencia tipoOcorrencia;
    private String ocorrencia;
    private EstadoOcorrencia estado;
    private LocalDateTime horaOcorrencia;

    public static OcorrenciasResponseDTO from(Ocorrencias ocorrencia) {
        OcorrenciasResponseDTO dto = new OcorrenciasResponseDTO();
        dto.id = ocorrencia.getId();
        dto.createDate = ocorrencia.getCreateDate();
        dto.createUser = ocorrencia.getCreateUser();
        dto.tipoOcorrencia = ocorrencia.getTipoOcorrencia();
        dto.ocorrencia = ocorrencia.getOcorrencia();
        dto.estado = ocorrencia.getEstadoOcorrencia();
        dto.horaOcorrencia = ocorrencia.getHoraOcorrencia();
        return dto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(String ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    public TipoOcorrencia getTipoOcorrencia() {
        return tipoOcorrencia;
    }

    public void setTipoOcorrencia(TipoOcorrencia tipoOcorrencia) {
        this.tipoOcorrencia = tipoOcorrencia;
    }

    public EstadoOcorrencia getEstado() {
        return estado;
    }

    public void setEstado(EstadoOcorrencia estado) {
        this.estado = estado;
    }

    public LocalDateTime getHoraOcorrencia() {
        return horaOcorrencia;
    }

    public void setHoraOcorrencia(LocalDateTime horaOcorrencia) {
        this.horaOcorrencia = horaOcorrencia;
    }
}