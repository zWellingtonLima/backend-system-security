package com.group1.gestao_seguranca.entities;

import com.group1.gestao_seguranca.enums.TipoConsumoEnum;
import jakarta.persistence.*;

import java.util.List;

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

    @OneToMany(mappedBy = "tipoConsumo", fetch = FetchType.LAZY)
    private List<Consumos> consumos;

    public TipoConsumo() {
    }

    public TipoConsumo(TipoConsumoEnum tipoConsumo, List<Consumos> consumos) {
        this.tipoConsumo = tipoConsumo;
        this.consumos = consumos;
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

    public List<Consumos> getConsumos() {
        return consumos;
    }

    public void setConsumos(List<Consumos> consumos) {
        this.consumos = consumos;
    }
}
