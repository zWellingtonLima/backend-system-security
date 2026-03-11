package com.group1.gestao_seguranca.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tipo_consumo")
public class TipoConsumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_consumo")
    private Integer id;
    @Column(name = "tipo")
    private String tipo;

    @OneToMany(mappedBy = "tipoConsumo", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Consumos> consumos;

    public TipoConsumo() {
    }

    public TipoConsumo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Consumos> getConsumos() {
        return consumos;
    }

    public void setConsumos(List<Consumos> consumos) {
        this.consumos = consumos;
    }
}
