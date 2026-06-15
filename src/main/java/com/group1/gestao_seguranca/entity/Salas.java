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

    public String getCodigoSala() {
        return codigoSala;
    }

    public void setCodigoSala(String codigoSala) {
        this.codigoSala = codigoSala;
    }

    public String getPiso() {
        return piso.name();
    }

    public void setPiso(PisoEnum piso) {
        this.piso = piso;
    }
}
