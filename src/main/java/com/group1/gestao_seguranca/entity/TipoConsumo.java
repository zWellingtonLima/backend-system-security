package com.group1.gestao_seguranca.entity;

import com.group1.gestao_seguranca.enums.TipoConsumoEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "tipo_consumo")
public class TipoConsumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_consumo")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoConsumoEnum tipoConsumo;

    public TipoConsumo() {
    }

    public TipoConsumo(TipoConsumoEnum tipoConsumo) {
        this.tipoConsumo = tipoConsumo;
    }

    public TipoConsumoEnum getTipoConsumo() {
        return tipoConsumo;
    }

    public String getLabel() {
        return this.tipoConsumo.getLabel();
    }

    public void setTipoConsumo(TipoConsumoEnum tipoConsumo) {
        this.tipoConsumo = tipoConsumo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
