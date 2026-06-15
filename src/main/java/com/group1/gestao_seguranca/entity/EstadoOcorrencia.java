package com.group1.gestao_seguranca.entity;

import com.group1.gestao_seguranca.enums.EstadoOcorrenciaEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "estado_ocorrencia")
public class EstadoOcorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_ocorrencia", length = 15, nullable = false, unique = true)
    private EstadoOcorrenciaEnum estadoOcorrencia;

    public EstadoOcorrencia() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEstadoOcorrencia() {
        return estadoOcorrencia.toString();
    }

    public void setEstadoOcorrencia(EstadoOcorrenciaEnum estado) {
        this.estadoOcorrencia = estado;
    }
}