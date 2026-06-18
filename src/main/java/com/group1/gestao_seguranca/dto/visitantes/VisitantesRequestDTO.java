package com.group1.gestao_seguranca.dto.visitantes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VisitantesRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 150)
        String nome,

        @NotBlank(message = "Documento de identificação é obrigatório")
        @Size(max = 30)
        String documentoIdentificacao,

        @Size(max = 100)
        String empresa,

        @Size(max = 500)
        String observacoes
) {
}