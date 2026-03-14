package com.group1.gestao_seguranca.entities;

import com.group1.gestao_seguranca.enums.TipoChaveEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tipo_chave")
public class TipoChave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_chave")
    private int id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoChaveEnum tipoChave;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sala", nullable = false)
    private Salas sala;

    @OneToMany(mappedBy = "tipoChave", fetch = FetchType.LAZY)
    private List<Chaves> chaves;

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

    public TipoChave() {
    }

    public TipoChave(TipoChaveEnum tipoChave, Salas sala) {
        this.tipoChave = tipoChave;
        this.sala = sala;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoChaveEnum getTipoChave() {
        return tipoChave;
    }

    public void setTipoChave(TipoChaveEnum tipoChave) {
        this.tipoChave = tipoChave;
    }

    public Salas getSala() {
        return sala;
    }

    public void setSala(Salas sala) {
        this.sala = sala;
    }

    public List<Chaves> getChaves() {
        return chaves;
    }

    public void setChaves(List<Chaves> chaves) {
        this.chaves = chaves;
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

