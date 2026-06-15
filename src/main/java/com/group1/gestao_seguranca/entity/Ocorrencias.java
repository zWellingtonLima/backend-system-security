package com.group1.gestao_seguranca.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group1.gestao_seguranca.enums.EstadoOcorrenciaEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ocorrencias")
public class Ocorrencias extends Auditable {

    @Column(name = "hora_ocorrencia", nullable = false)
    private LocalDateTime horaOcorrencia;

    @Column(length = 500, nullable = false) // TODO: tornar campo obrigatorio na BD
    private String ocorrencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    @JsonIgnore
    private User seguranca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_ocorrencia", nullable = false)
    @JsonIgnore
    private TipoOcorrencia tipoOcorrencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado_ocorrencia", nullable = false)
    @JsonIgnore
    private EstadoOcorrencia estadoOcorrencia;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "data_exclusao")
    private LocalDateTime dataExclusao;

    public Ocorrencias() {
    }

    public boolean isAtivo() {
        return ativo;
    }

    public LocalDateTime getDataExclusao() {
        return dataExclusao;
    }

    public void desativar() {
        this.ativo = false;
        this.dataExclusao = LocalDateTime.now();
    }

    public void reativar() {
        this.ativo = true;
        this.dataExclusao = null;
    }

    public LocalDateTime getHoraOcorrencia() {
        return horaOcorrencia;
    }

    public void setHoraOcorrencia(LocalDateTime horaOcorrencia) {
        this.horaOcorrencia = horaOcorrencia;
    }

    public String getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(String ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    public User getSeguranca() {
        return seguranca;
    }

    public void setSeguranca(User seguranca) {
        this.seguranca = seguranca;
    }

    public String getEstadoOcorrencia() {
        return estadoOcorrencia.getEstadoOcorrencia();
    }

    public void setEstadoOcorrencia(EstadoOcorrenciaEnum estado) {
        estadoOcorrencia.setEstadoOcorrencia(estado);
    }
}