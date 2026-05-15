package com.group1.gestao_seguranca.entities;

import com.group1.gestao_seguranca.enums.StatusChaveEnum;
import com.group1.gestao_seguranca.enums.TipoChaveEnum;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "chaves")
public class Chaves extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chave")
    private int id;

    @Column(name = "codigo_chave")
    private String codigoChave;

    @Column(name = "codigo_molho")
    private String codigoMolho;

    @Enumerated(EnumType.STRING)
    private StatusChaveEnum status;

    @Column(length = 100)
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_chave", nullable = false)
    private TipoChave tipoChave;

    // Chave de sala
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sala")
    private Salas sala;

    // Histórico de entregas de uma chave
    @OneToMany(mappedBy = "chave", fetch = FetchType.LAZY)
    private List<EntregaChaves> historicoEntregas;

    public Chaves() {
    }

    public Chaves(String codigoChave, StatusChaveEnum statusChave, TipoChave tipoChave, Salas sala) {
        this.codigoChave = codigoChave;
        this.status = statusChave;
        this.tipoChave = tipoChave;
        this.sala = sala;
    }

    public StatusChaveEnum getStatus() {
        return status;
    }

    public String getCodigoMolho() {
        return codigoMolho;
    }

    public void setCodigoMolho(String codigoMolho) {
        this.codigoMolho = codigoMolho;
    }

    public void setStatus(StatusChaveEnum status) {
        this.status = status;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StatusChaveEnum getStatusChave() {
        return status;
    }

    public void setStatusChave(StatusChaveEnum statusChave) {
        this.status = statusChave;
    }

    public String getChaveLabel(StatusChaveEnum statusChave) {
        return statusChave.getLabel();
    }

    public Salas getSala() {
        return sala;
    }

    public void setSala(Salas sala) {
        this.sala = sala;
    }

    public String getCodigoChave() {
        return codigoChave;
    }

    public void setCodigoChave(String codigoChave) {
        this.codigoChave = codigoChave;
    }

    // Atenção: dispara lazy load do TipoChave se a entidade não foi
    // carregada com JOIN FETCH em tipoChave. Em listagens, garantir
    // o JOIN FETCH para evitar N+1.
    public TipoChaveEnum getTipoChave() {
        return tipoChave.getTipoChave();
    }

    public void setTipoChave(TipoChave tipoChave) {
        this.tipoChave = tipoChave;
    }

    public List<EntregaChaves> getHistoricoEntregas() {
        return historicoEntregas;
    }

    public void setHistoricoEntregas(List<EntregaChaves> historicoEntregas) {
        this.historicoEntregas = historicoEntregas;
    }
}
