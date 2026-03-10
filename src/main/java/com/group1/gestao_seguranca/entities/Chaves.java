package com.group1.gestao_seguranca.entities;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "status_chave")
    private StatusChave statusChave;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_chave", nullable = false)
    private TipoChave tipoChave;

    @OneToMany(mappedBy = "chave", fetch = FetchType.LAZY)
    private List<EntregaChaves> emprestimosDeChaves;

    @Column(name = "create_user")
    private String createUser;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "modify_user")
    private String modifyUser;
    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

    // Mostra o historico de entregas de uma chave
    @OneToMany(mappedBy = "chave", fetch = FetchType.LAZY)
    private List<EntregaChaves> historicoEntregas;

    public Chaves() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<EntregaChaves> getEmprestimosDeChaves() {
        return emprestimosDeChaves;
    }

    public void setEmprestimosDeChaves(List<EntregaChaves> emprestimosDeChaves) {
        this.emprestimosDeChaves = emprestimosDeChaves;
    }

    public String getCodigoChave() {
        return codigoChave;
    }

    public void setCodigoChave(String codigoChave) {
        this.codigoChave = codigoChave;
    }

    public StatusChave getStatusChave() {
        return statusChave;
    }

    public void setStatusChave(StatusChave statusChave) {
        this.statusChave = statusChave;
    }

    public TipoChave getTipoChave() {
        return tipoChave;
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

enum StatusChave {
    DISPONIVEL, EMPRESTADA, PERDIDA, MANUTENCAO
}
