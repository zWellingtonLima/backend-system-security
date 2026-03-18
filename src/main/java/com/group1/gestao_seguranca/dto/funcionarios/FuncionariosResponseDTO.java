package com.group1.gestao_seguranca.dto.funcionarios;

import com.group1.gestao_seguranca.entities.Funcionarios;

import java.time.LocalDateTime;

public class FuncionariosResponseDTO {

    private int id;
    private String nomeFuncionario;
    private String numeroFuncionario;
    private String setor;
    private LocalDateTime registadoEm;

    public static FuncionariosResponseDTO from(Funcionarios f) {
        FuncionariosResponseDTO dto = new FuncionariosResponseDTO();
        dto.id = f.getId();
        dto.nomeFuncionario = f.getNomeFuncionario();
        dto.numeroFuncionario = f.getNumeroFuncionario();
        dto.setor = f.getSetor();
        dto.registadoEm = f.getCreateDate();

        return dto;
    }

    // getters
    public int getId() {
        return id;
    }

    public LocalDateTime getRegistadoEm() {
        return registadoEm;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public String getNumeroFuncionario() {
        return numeroFuncionario;
    }

    public String getSetor() {
        return setor;
    }
}
