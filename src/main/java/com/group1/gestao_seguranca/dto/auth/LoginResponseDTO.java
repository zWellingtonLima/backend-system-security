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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }
}
