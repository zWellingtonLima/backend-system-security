package com.group1.gestao_seguranca.entity;

import com.group1.gestao_seguranca.enums.TipoOcorrenciaEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "tipo_ocorrencia")
public class TipoOcorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 60)
    private TipoOcorrenciaEnum tipo;

    public TipoOcorrencia() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo.toString();
    }

    public void setTipo(TipoOcorrenciaEnum tipo) {
        this.tipo = tipo;
    }
}

