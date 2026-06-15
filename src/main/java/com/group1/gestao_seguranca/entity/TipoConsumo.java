package com.group1.gestao_seguranca.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_consumo")
public class TipoConsumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 20, nullable = false)
    private String tipoConsumo; // AGUA, ELETRICIDADE OU GAS

    public TipoConsumo() {
    }

    public String getTipoConsumo() {
        return tipoConsumo;
    }

    public void setTipoConsumo(String tipoConsumo) {
        this.tipoConsumo = tipoConsumo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
