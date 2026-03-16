package com.group1.gestao_seguranca.dto.funcionarios;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class FuncionariosRequestDTO {

    @NotBlank(message = "Nome do funcionário é obrigatório")
    @Size(max = 150)
    private String nomeFuncionario;

    @NotBlank(message = "Número de funcionário é obrigatório")
    @Size(max = 6)
    private String numeroFuncionario;

    @NotBlank(message = "Setor é obrigatório")
    @Size(max = 50)
    private String setor;

    // getters e setters
    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getNumeroFuncionario() {
        return numeroFuncionario;
    }

    public void setNumeroFuncionario(String numeroFuncionario) {
        this.numeroFuncionario = numeroFuncionario;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }
}
