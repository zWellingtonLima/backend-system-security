package com.group1.gestao_seguranca.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chaves")
public class Chaves extends Auditable {
    @Column(nullable = false, unique = true, length = 20)
    private String codigo;

    @Column(length = 100)
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sala")
    private Salas sala;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_chave", nullable = false)
    private TipoChave tipoChave;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "data_exclusao")
    private LocalDateTime dataExclusao;

    public Chaves() {
    }

    public boolean isAtivo() {
        return ativo;
    }

    public LocalDateTime getDataExclusao() {
        return dataExclusao;
    }

    public void desativar() {
        this.ativo = false;
        this.dataExclusao = LocalDateTime.now();
    }

    public void reativar() {
        this.ativo = true;
        this.dataExclusao = null;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getTipoChave() {
        return tipoChave.getTipoChave();
    }

    public Salas getSala() {
        return sala;
    }
}
