package com.group1.gestao_seguranca.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ocorrencias")
public class Ocorrencias extends Auditable {

    @Column(name = "hora_ocorrencia")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime horaOcorrencia;

    @Column(name = "ocorrencia", length = 500)
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
    @JoinColumn(name = "fk_id_tipo_estado_ocorrencia", nullable = false)
    @JsonIgnore
    private EstadoOcorrencia estadoOcorrencia;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "data_exclusao")
    private LocalDateTime dataExclusao;

    public Ocorrencias() {
    }

    public Ocorrencias(String ocorrencia, User seguranca) {
        this.ocorrencia = ocorrencia;
        this.seguranca = seguranca;
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

    public TipoOcorrencia getTipoOcorrencia() {
        return tipoOcorrencia;
    }

    public void setTipoOcorrencia(TipoOcorrencia tipoOcorrencia) {
        this.tipoOcorrencia = tipoOcorrencia;
    }

    public EstadoOcorrencia getEstadoOcorrencia() {
        return estadoOcorrencia;
    }

    public void setEstadoOcorrencia(EstadoOcorrencia estadoOcorrencia) {
        this.estadoOcorrencia = estadoOcorrencia;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataExclusao() {
        return dataExclusao;
    }

    public void setDataExclusao(LocalDateTime dataExclusao) {
        this.dataExclusao = dataExclusao;
    }
}  // ← único fecho da classe