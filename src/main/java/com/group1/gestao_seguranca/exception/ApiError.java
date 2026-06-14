package com.group1.gestao_seguranca.exception;

import java.time.LocalDateTime;
import java.util.List;

public class ApiError {

    private int status;
    private String erro;
    private List<String> mensagens;
    private LocalDateTime timestamp;

    public ApiError(int status, String erro, List<String> mensagens) {
        this.status = status;
        this.erro = erro;
        this.mensagens = mensagens;
        this.timestamp = LocalDateTime.now();
    }

    // Construtor para erro único
    public ApiError(int status, String erro, String mensagem) {
        this(status, erro, List.of(mensagem));
    }

    public int getStatus() {
        return status;
    }

    public String getErro() {
        return erro;
    }

    public List<String> getMensagens() {
        return mensagens;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}