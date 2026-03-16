package com.group1.gestao_seguranca.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "visitantes")
public class Visitantes extends Pessoa {
    @Column(name = "nome_visitante", nullable = false, length = 150)
    private String nomeVisitante;

    @Column(name = "empresa", length = 100)
    private String empresa;

    @Column(name = "documento_identificacao", nullable = false, length = 30)
    private String documentoIdentificacao;

    @Column(name = "setor_destino", length = 100)
    private String setorDestino;

    @OneToMany(mappedBy = "visitante", fetch = FetchType.LAZY)
    private List<Visitas> visitas;

    @Column(name="create_user")
    private String createUser;
    @Column(name="create_date")
    private LocalDateTime createDate;
    @Column(name="modify_user")
    private String modifyUser;
    @Column(name="modify_date")
    private LocalDateTime modifyDate;

    @PrePersist
    protected void onCreate() {
        this.createDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifyDate = LocalDateTime.now();
    }

    public Visitantes() {
    }

    public Visitantes(String nomeVisitante, String documentoIdentificacao) {
        this.nomeVisitante = nomeVisitante;
        this.documentoIdentificacao = documentoIdentificacao;
    }

    public Visitantes(String nomeVisitante, String empresa, String documentoIdentificacao, String setorDestino) {
        this.nomeVisitante = nomeVisitante;
        this.empresa = empresa;
        this.documentoIdentificacao = documentoIdentificacao;
        this.setorDestino = setorDestino;
    }

    public List<Visitas> getVisitas() {
        return visitas;
    }

    public void setVisitas(List<Visitas> visitas) {
        this.visitas = visitas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeVisitante() {
        return nomeVisitante;
    }

    public void setNomeVisitante(String nomeVisitante) {
        this.nomeVisitante = nomeVisitante;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getDocumentoIdentificacao() {
        return documentoIdentificacao;
    }

    public void setDocumentoIdentificacao(String documentoIdentificacao) {
        this.documentoIdentificacao = documentoIdentificacao;
    }

    public String getSetorDestino() {
        return setorDestino;
    }

    public void setSetorDestino(String setorDestino) {
        this.setorDestino = setorDestino;
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
}
