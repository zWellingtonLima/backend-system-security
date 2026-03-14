package com.group1.gestao_seguranca.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequestDTO {

    @NotBlank(message = "Nome do segurança é obrigatório")
    @Size(max = 150)
    private String nomeSeguranca;
    @NotBlank(message = "Número do segurança é obrigatório")
    private Integer numeroSeguranca;
    @NotBlank(message = "Palavra-passe é obrigatória")
    private String password;

    public Integer getNumeroSeguranca() {
        return numeroSeguranca;
    }

    public void setNumeroSeguranca(Integer numeroSeguranca) {
        this.numeroSeguranca = numeroSeguranca;
    }

    public String getNomeSeguranca() {
        return nomeSeguranca;
    }

    public void setNomeSeguranca(String nomeSeguranca) {
        this.nomeSeguranca = nomeSeguranca;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
