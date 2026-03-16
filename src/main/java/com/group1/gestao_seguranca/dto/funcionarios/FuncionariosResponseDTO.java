package com.group1.gestao_seguranca.dto.funcionarios;

import com.group1.gestao_seguranca.entities.Funcionarios;

public class FuncionariosResponseDTO {

    private int id;
    private String nomeFuncionario;
    private String numeroFuncionario;
    private String setor;

    public static FuncionariosResponseDTO from(Funcionarios f) {
        FuncionariosResponseDTO dto = new FuncionariosResponseDTO();
        dto.id = f.getId();
        dto.nomeFuncionario = f.getNomeFuncionario();
        dto.numeroFuncionario = f.getNumeroFuncionario();
        dto.setor = f.getSetor();
        return dto;
    }

    // getters
    public int getId() {
        return id;
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
