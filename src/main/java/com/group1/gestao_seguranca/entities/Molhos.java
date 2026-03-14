package com.group1.gestao_seguranca.entities;

import com.group1.gestao_seguranca.enums.StatusMolhoEnum;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "molhos")
public class Molhos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_molho")
    private int id;

    @Column(name = "nome_molho", nullable = false)
    private String nomeMolho;

    private String localizacao;
    private String departamento;
    private String observacoes;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_molho", nullable = false, length = 15)
    private StatusMolhoEnum statusMolho;

    @OneToMany(mappedBy = "molho", fetch = FetchType.LAZY)
    private List<Chaves> chaves;

    @OneToMany(mappedBy = "molho", fetch = FetchType.LAZY)
    private List<EntregaChaves> historicoEntregas;

    public Molhos() {
    }

    public Molhos(String nomeMolho, String localizacao, String departamento, String observacoes, StatusMolhoEnum statusMolho) {
        this.nomeMolho = nomeMolho;
        this.localizacao = localizacao;
        this.departamento = departamento;
        this.observacoes = observacoes;
        this.statusMolho = statusMolho;
    }

    public List<EntregaChaves> getHistoricoEntregas() {
        return historicoEntregas;
    }

    public void setHistoricoEntregas(List<EntregaChaves> historicoEntregas) {
        this.historicoEntregas = historicoEntregas;
    }

    public StatusMolhoEnum getStatusMolho() {
        return statusMolho;
    }

    public void setStatusMolho(StatusMolhoEnum statusMolho) {
        this.statusMolho = statusMolho;
    }

    public List<Chaves> getChaves() {
        return chaves;
    }

    public void setChaves(List<Chaves> chaves) {
        this.chaves = chaves;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeMolho() {
        return nomeMolho;
    }

    public void setNomeMolho(String nomeMolho) {
        this.nomeMolho = nomeMolho;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
