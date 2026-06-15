package com.group1.gestao_seguranca.entity;

import com.group1.gestao_seguranca.enums.TipoVisitaEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "visitas")
public class Visitas extends Auditable {

    @Column(name = "setor_destino")
    private String setorDestino; // TODO: verificar possibilidade implementar tabela lookup para setores

    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_visitante", nullable = false)
    private Visitantes visitante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_visita", nullable = false)
    private TipoVisita tipoVisita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcionario_responsavel", nullable = false)
    private Funcionarios funcionarioResponsavel;

    public Visitas() {
    }

    public String getSetorDestino() {
        return setorDestino;
    }

    public void setSetorDestino(String setorDestino) {
        this.setorDestino = setorDestino;
    }

    public String getTipoVisita() {
        return tipoVisita.getTipo();
    }

    public void setTipoVisita(TipoVisitaEnum tipo) {
        tipoVisita.setTipo(tipo);
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
