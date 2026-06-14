package com.group1.gestao_seguranca.entity;

import com.group1.gestao_seguranca.enums.PisoEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "salas")
public class Salas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codigo_sala", nullable = false, unique = true)
    private String codigoSala;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private PisoEnum piso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_edificio", nullable = false)
    private Edificio edificio;

    public Salas() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoSala() {
        return codigoSala;
    }

    public void setCodigoSala(String codigoSala) {
        this.codigoSala = codigoSala;
    }

    public PisoEnum getPiso() {
        return piso;
    }

    public void setPiso(PisoEnum piso) {
        this.piso = piso;
    }
}
