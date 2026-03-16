package com.group1.gestao_seguranca.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "funcionarios")
public class Funcionarios extends Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcionario")
    private int id;

    @Column(nullable = false, length = 150)
    private String nomeFuncionario;

    @Column(nullable = false, length = 50)
    private String setor;

    @Column(name="create_user")
    private String createUser;
    @Column(name="create_date")
    private LocalDateTime createDate;
    @Column(name="modify_user")
    private String modifyUser;
    @Column(name="modify_date")
    private LocalDateTime modifyDate;

    @OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY)
    private List<Movimentacoes> funcionarioMovimentacoes;

    @OneToMany(mappedBy = "funcionarioComChave", fetch = FetchType.LAZY)
    private List<EntregaChaves> chavesRecebidasFuncionario;

    @OneToMany(mappedBy = "funcionarioResponsavel", fetch = FetchType.LAZY)
    private List<Visitas> visitasResponsavel;

    @PrePersist
    protected void onCreate() {
        this.createDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifyDate = LocalDateTime.now();
    }

    public Funcionarios() {
    }

    public Funcionarios(String nomeFuncionario, String setor) {
        this.nomeFuncionario = nomeFuncionario;
        this.setor = setor;
    }

    public List<Visitas> getVisitasResponsavel() {
        return visitasResponsavel;
    }

    public void setVisitasResponsavel(List<Visitas> visitasResponsavel) {
        this.visitasResponsavel = visitasResponsavel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
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
