package com.group1.gestao_seguranca.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "consumos")
public class Consumos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consumo")
    private int id;

    @Column(name="valor_leitura" ,nullable = false)
    private int valorLeitura;
    @Column(name="data_registo")
    private LocalDateTime dataRegisto;
    @Column(name="observacao")
    private String observacao;

    @Enumerated(EnumType.STRING)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_consumo", nullable = false)
    @JsonManagedReference
    private TipoConsumo tipoConsumo;

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

    public Consumos() {
    }

    public Consumos(int valorLeitura, LocalDateTime dataRegisto, String observacao, TipoConsumo tipoConsumo) {
        this.valorLeitura = valorLeitura;
        this.dataRegisto = dataRegisto;
        this.observacao = observacao;
        this.tipoConsumo = tipoConsumo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValorLeitura() {
        return valorLeitura;
    }

    public void setValorLeitura(int valorLeitura) {
        this.valorLeitura = valorLeitura;
    }

    public LocalDateTime getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(LocalDateTime dataRegisto) {
        this.dataRegisto = dataRegisto;
    }

    public String getObservacoes() {
        return observacao;
    }

    public void setObservacoes(String observacoes) {
        this.observacao = observacao;
    }

    public TipoConsumo getTipoConsumo() {
        return tipoConsumo;
    }

    public void setTipoConsumo(TipoConsumo tipoConsumo) {
        this.tipoConsumo = tipoConsumo;
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