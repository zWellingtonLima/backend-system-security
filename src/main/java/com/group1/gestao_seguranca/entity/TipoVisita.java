package com.group1.gestao_seguranca.entity;

import com.group1.gestao_seguranca.enums.TipoVisitaEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "tipo_visita")
public class TipoVisita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipoVisitaEnum tipo;

    public TipoVisita() {
    }

    public String getTipo() {
        return tipo.toString();
    }

    public void setTipo(TipoVisitaEnum tipoVisita) {
        this.tipo = tipoVisita;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
