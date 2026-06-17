package com.group1.gestao_seguranca.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String mensagem) {
        super(mensagem);
    }
}