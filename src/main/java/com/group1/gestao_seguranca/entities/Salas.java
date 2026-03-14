package com.group1.gestao_seguranca.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group1.gestao_seguranca.enums.PisoEnum;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "salas")
public class Salas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sala")
    private int id;

    @Column(name = "numero_sala", nullable = false, unique = true)
    private int numeroSala;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private PisoEnum piso;

    @JsonIgnore
    @OneToMany(mappedBy = "sala", fetch = FetchType.LAZY)
    private List<Chaves> chaves;

    public Salas() {
    }

    public Salas(int numeroSala, PisoEnum piso) {
        this.numeroSala = numeroSala;
        this.piso = piso;
    }

    public List<Chaves> getChaves() {
        return chaves;
    }

    public void setChaves(List<Chaves> chaves) {
        this.chaves = chaves;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroSala() {
        return numeroSala;
    }

    public void setNumeroSala(int numeroSala) {
        this.numeroSala = numeroSala;
    }

    public PisoEnum getPiso() {
        return piso;
    }

    public void setPiso(PisoEnum piso) {
        this.piso = piso;
    }
}
