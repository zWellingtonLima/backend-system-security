package com.group1.gestao_seguranca.entities;

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
    private Piso piso;

    @OneToMany(mappedBy = "sala", fetch = FetchType.LAZY)
    private List<Chaves> chaves;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sala", nullable = false)
    private Salas sala;

    public Salas() {
    }

    public Salas(int numeroSala, Piso piso) {
        this.numeroSala = numeroSala;
        this.piso = piso;
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

    public Piso getPiso() {
        return piso;
    }

    public void setPiso(Piso piso) {
        this.piso = piso;
    }
}

enum Piso {
    PRIMEIRO, SEGUNDO, TERCEIRO;
}