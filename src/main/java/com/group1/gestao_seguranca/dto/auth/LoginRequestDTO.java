package com.group1.gestao_seguranca.dto.auth;

import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
