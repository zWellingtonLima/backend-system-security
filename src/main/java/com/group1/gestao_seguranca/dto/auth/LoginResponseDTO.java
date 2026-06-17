package com.group1.gestao_seguranca.dto.auth;

public class LoginResponseDTO {
    private String accessToken;
    private int userId;
    private String nome;

    public LoginResponseDTO(String accessToken, int userId, String nome) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.nome = nome;
    }
}
