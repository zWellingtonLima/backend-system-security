package com.group1.gestao_seguranca.entities;

import com.group1.gestao_seguranca.enums.EstadoOcorrenciaEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "tipo_estado_ocorrencia")
public class EstadoOcorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_ocorrencia", length = 50, nullable = false, unique = true)
    private EstadoOcorrenciaEnum estadoOcorrencia;

    public EstadoOcorrencia() {
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EstadoOcorrenciaEnum getEstadoOcorrencia() {
        return estadoOcorrencia;
    }

    public void setEstadoOcorrencia(EstadoOcorrenciaEnum estadoOcorrencia) {
        this.estadoOcorrencia = estadoOcorrencia;
    }

    public String getLabel() {
        return estadoOcorrencia != null ? estadoOcorrencia.getLabel() : null;
    }
}