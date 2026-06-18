package com.group1.gestao_seguranca.dto.visitantes;

import com.group1.gestao_seguranca.entity.Visitantes;

import java.time.LocalDateTime;

public record VisitantesResponseDTO(
        Integer id,
        String nomeVisitante,
        String documentoIdentificacao,
        String empresa,
        String observacoes,
        LocalDateTime registadoEm
) {

    public static VisitantesResponseDTO from(Visitantes v) {
        return new VisitantesResponseDTO(
                v.getId(),
                v.getNome(),
                v.getDocumentoIdentificacao(),
                v.getEmpresa(),
                v.getObservacoes(),
                v.getCreateDate()
        );
    }
}
