package com.group1.gestao_seguranca.dto.ocorrencias;

import com.group1.gestao_seguranca.entities.Ocorrencias;

import java.time.LocalDateTime;

public class OcorrenciasResponseDTO {

    private Integer id;
    private LocalDateTime createDate;
    private String createUser;
    private String tipoOcorrencia;
    private String ocorrencia;
    private String estado;
    private LocalDateTime horaOcorrencia;

    public static OcorrenciasResponseDTO from(Ocorrencias ocorrencia) {
        OcorrenciasResponseDTO dto = new OcorrenciasResponseDTO();
        dto.id = ocorrencia.getId();
        dto.createDate = ocorrencia.getCreateDate();
        dto.createUser = ocorrencia.getCreateUser();
        dto.tipoOcorrencia = ocorrencia.getTipoOcorrencia().getLabel();
        dto.ocorrencia = ocorrencia.getOcorrencia();
        dto.estado = ocorrencia.getEstado().getLabel();
        dto.horaOcorrencia = ocorrencia.getHoraOcorrencia();
        return dto;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDateTime getCreateDate() { return createDate; }
    public void setCreateDate(LocalDateTime createDate) { this.createDate = createDate; }

    public String getCreateUser() { return createUser; }
    public void setCreateUser(String createUser) { this.createUser = createUser; }

    public String getTipoOcorrencia() { return tipoOcorrencia; }
    public void setTipoOcorrencia(String tipoOcorrencia) { this.tipoOcorrencia = tipoOcorrencia; }

    public String getOcorrencia() { return ocorrencia; }
    public void setOcorrencia(String ocorrencia) { this.ocorrencia = ocorrencia; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getHoraOcorrencia() { return horaOcorrencia; }
    public void setHoraOcorrencia(LocalDateTime horaOcorrencia) { this.horaOcorrencia = horaOcorrencia; }
}