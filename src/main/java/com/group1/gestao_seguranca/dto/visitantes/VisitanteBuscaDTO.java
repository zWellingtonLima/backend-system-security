package com.group1.gestao_seguranca.dto.visitantes;

import com.group1.gestao_seguranca.entity.Visitantes;

public record VisitanteBuscaDTO(
        Integer id,
        String nomeVisitante,
        String empresa,
        String documentoIdentificacao
) {
    public static VisitanteBuscaDTO from(Visitantes v) {
        return new VisitanteBuscaDTO(
                v.getId(),
                v.getNome(),
                v.getEmpresa(),
                v.getDocumentoIdentificacao()
        );
    }
}
