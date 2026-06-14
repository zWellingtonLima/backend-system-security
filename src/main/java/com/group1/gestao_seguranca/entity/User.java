package com.group1.gestao_seguranca.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User extends Auditable {

    @Column(name = "nome_seguranca", nullable = false, length = 150)
    private String nomeSeguranca;
    @Column(name = "numero_seguranca", nullable = false, unique = true)
    private Integer numeroSeguranca;
    @Column(nullable = false, length = 30)
    private String password;

    @OneToMany(mappedBy = "seguranca", fetch = FetchType.LAZY)
    private List<Ocorrencias> ocorrencias;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Sessao> sessoes;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Consumos> consumos;

    public User() {
    }

    public User(String nomeSeguranca, int numeroSeguranca, String password) {
        this.nomeSeguranca = nomeSeguranca;
        this.numeroSeguranca = numeroSeguranca;
        this.password = password;
    }

    public List<Sessao> getSessoes() {
        return sessoes;
    }

    public void setSessoes(List<Sessao> sessoes) {
        this.sessoes = sessoes;
    }

    public List<Consumos> getConsumos() {
        return consumos;
    }

    public void setConsumos(List<Consumos> consumos) {
        this.consumos = consumos;
    }


    public void setNumeroSeguranca(Integer numeroSeguranca) {
        this.numeroSeguranca = numeroSeguranca;
    }

    public List<Ocorrencias> getOcorrencias() {
        return ocorrencias;
    }

    public void setOcorrencias(List<Ocorrencias> ocorrencias) {
        this.ocorrencias = ocorrencias;
    }

    public String getNomeSeguranca() {
        return nomeSeguranca;
    }

    public void setNomeSeguranca(String nomeSeguranca) {
        this.nomeSeguranca = nomeSeguranca;
    }

    public int getNumeroSeguranca() {
        return numeroSeguranca;
    }

    public void setNumeroSeguranca(int numeroSeguranca) {
        this.numeroSeguranca = numeroSeguranca;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
