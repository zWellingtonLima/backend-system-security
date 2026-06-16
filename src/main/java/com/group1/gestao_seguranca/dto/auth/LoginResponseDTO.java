package com.group1.gestao_seguranca.dto.auth;

public class LoginResponseDTO {
    private String token;
    private Integer idUser;
    private String nomeUsuario;

    public LoginResponseDTO(String token, Integer idUser, String nomeUsuario) {
        this.token = token;
        this.idUser = idUser;
        this.nomeUsuario = nomeUsuario;
    }
}
