package com.group1.gestao_seguranca.entities;

import com.group1.gestao_seguranca.enums.StatusChaveEnum;
import com.group1.gestao_seguranca.enums.TipoChaveEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "chaves")
public class Chaves {
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

    @Column(name = "create_user")
    private String createUser;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "modify_user")
    private String modifyUser;
    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

    @PrePersist
    protected void onCreate() {
        this.createDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifyDate = LocalDateTime.now();
    }

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

    public TipoChaveEnum getTipoChave() {
        return tipoChave.getTipoChave();
    }

    public void setTipoChave(TipoChave tipoChave) {
        this.tipoChave = tipoChave;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public List<EntregaChaves> getHistoricoEntregas() {
        return historicoEntregas;
    }

    public void setHistoricoEntregas(List<EntregaChaves> historicoEntregas) {
        this.historicoEntregas = historicoEntregas;
    }
}
