package com.group1.gestao_seguranca.dto.visitantes;

import com.group1.gestao_seguranca.entities.Visitantes;

import java.time.LocalDateTime;

public class VisitantesResponseDTO {

    private int id;
    private String nomeVisitante;
    private String documentoIdentificacao;
    private String empresa;
    private String observacoes;
    private LocalDateTime registadoEm;

    public static VisitantesResponseDTO from(Visitantes v) {
        VisitantesResponseDTO dto = new VisitantesResponseDTO();
        dto.id = v.getId();
        dto.nomeVisitante = v.getNomeVisitante();
        dto.documentoIdentificacao = v.getDocumentoIdentificacao();
        dto.empresa = v.getEmpresa();
        dto.observacoes = v.getObservacoes();
        dto.registadoEm = v.getCreateDate();

        return dto;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getRegistadoEm() {
        return registadoEm;
    }

    public String getNomeVisitante() {
        return nomeVisitante;
    }

    public String getDocumentoIdentificacao() {
        return documentoIdentificacao;
    }

    public String getEmpresa() {
        return empresa;
    }

    public String getObservacoes() {
        return observacoes;
    }

}
