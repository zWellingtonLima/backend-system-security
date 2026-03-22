package com.group1.gestao_seguranca.dto.movimentacoes;

import com.group1.gestao_seguranca.entities.EntregaChaves;
import com.group1.gestao_seguranca.enums.TipoChaveEnum;

import java.time.LocalDateTime;

public class DevolucaoResponseDTO {

    private int idEntrega;    // null se for chave
    private TipoChaveEnum tipo;          // "CHAVE" ou "MOLHO"
    private LocalDateTime horaEntrega;
    private LocalDateTime horaDevolucao;
    private String devolvidaPor;
    private String observacoes;

    public static DevolucaoResponseDTO from(EntregaChaves entrega) {
        DevolucaoResponseDTO dto = new DevolucaoResponseDTO();
        dto.idEntrega = entrega.getId();
        dto.horaEntrega = entrega.getHoraEntrega();
        dto.horaDevolucao = entrega.getHoraDevolucao();
        dto.devolvidaPor = entrega.getDevolvidaPor();
        dto.observacoes = entrega.getObservacoes();
        dto.tipo = entrega.getChave().getTipoChave();

        return dto;
    }

    // getters
    public int getIdEntrega() {
        return idEntrega;
    }

    public TipoChaveEnum getTipo() {
        return tipo;
    }

    public LocalDateTime getHoraEntrega() {
        return horaEntrega;
    }

    public LocalDateTime getHoraDevolucao() {
        return horaDevolucao;
    }

    public String getDevolvidaPor() {
        return devolvidaPor;
    }

    public String getObservacoes() {
        return observacoes;
    }
}