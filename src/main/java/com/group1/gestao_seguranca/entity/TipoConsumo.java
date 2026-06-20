package com.group1.gestao_seguranca.entity;

import com.group1.gestao_seguranca.enums.TipoConsumoEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "tipo_consumo")
public class TipoConsumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private TipoConsumoEnum tipo; // AGUA, ELETRICIDADE OU GAS

    public TipoConsumo() {
    }

    public TipoConsumoEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoConsumoEnum tipo) {
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
