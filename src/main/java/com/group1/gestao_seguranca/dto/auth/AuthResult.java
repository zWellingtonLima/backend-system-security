package com.group1.gestao_seguranca.dto.auth;

public record AuthResult(
        LoginResponseDTO dto,
        String refreshToken
) {
}
