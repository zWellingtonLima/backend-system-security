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

    private String codigoChave;

    @Enumerated(EnumType.STRING)
    private StatusChave statusChave;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_chave", nullable = false)
    private TipoChave tipoChave;

    @OneToMany(mappedBy = "chave", fetch = FetchType.LAZY)
    private List<EntregaChaves> emprestimosDeChaves;

    private String create_user;
    private LocalDateTime create_date;
    private String modify_user;
    private LocalDateTime modify_date;

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

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    public LocalDateTime getCreate_date() {
        return create_date;
    }

    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
    }

    public String getModify_user() {
        return modify_user;
    }

    public void setModify_user(String modify_user) {
        this.modify_user = modify_user;
    }

    public LocalDateTime getModify_date() {
        return modify_date;
    }

    public void setModify_date(LocalDateTime modify_date) {
        this.modify_date = modify_date;
    }
}

enum StatusChave {
    DISPONIVEL, EMPRESTADA, PERDIDA, MANUTENCAO
}
