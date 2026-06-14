package com.group1.gestao_seguranca.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_chave")
public class TipoChave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "tipo_chave", length = 15)
    private String tipoChave; // CHAVE OU MOLHO

    public TipoChave() {
    }

    public String getTipoChave() {
        return tipoChave;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

