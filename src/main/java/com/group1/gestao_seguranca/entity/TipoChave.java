package com.group1.gestao_seguranca.entity;

import com.group1.gestao_seguranca.enums.TipoChaveEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "tipo_chave")
public class TipoChave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "tipo_chave", length = 15)
    private TipoChaveEnum tipoChave; // CHAVE OU MOLHO

    public TipoChave() {
    }

    public TipoChaveEnum getTipoChave() {
        return tipoChave;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

