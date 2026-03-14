package com.group1.gestao_seguranca.entities;

import com.group1.gestao_seguranca.enums.TipoOcorrenciaEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "tipo_ocorrencia")
public class TipoOcorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_ocorrencia")
    private int id;

    @Enumerated(EnumType.STRING)
    private TipoOcorrenciaEnum tipoOcorrencia;

    public TipoOcorrencia() {
    }

    public TipoOcorrencia(TipoOcorrenciaEnum tipoOcorrencia) {
        this.tipoOcorrencia = tipoOcorrencia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoOcorrenciaEnum getTipoOcorrencia() {
        return tipoOcorrencia;
    }

    public void setTipoOcorrencia(TipoOcorrenciaEnum tipoOcorrencia) {
        this.tipoOcorrencia = tipoOcorrencia;
    }
}

