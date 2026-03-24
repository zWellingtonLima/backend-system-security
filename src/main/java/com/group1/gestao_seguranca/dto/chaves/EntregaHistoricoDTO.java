package com.group1.gestao_seguranca.dto.chaves;

import com.group1.gestao_seguranca.entities.EntregaChaves;
import com.group1.gestao_seguranca.enums.TipoChaveEnum;

import java.time.LocalDateTime;

public class EntregaHistoricoDTO {

    private int idEntrega;
    private String descricao;
    private TipoChaveEnum tipo;
    private String sala;
    private String nomePessoa;
    private LocalDateTime horaEntrega;
    private LocalDateTime horaDevolucao;
    private String devolvidaPor;
    private String observacoes;

    public static EntregaHistoricoDTO from(EntregaChaves e) {
        EntregaHistoricoDTO dto = new EntregaHistoricoDTO();
        dto.idEntrega = e.getId();
        dto.tipo = e.getChave().getTipoChave();
        dto.horaEntrega = e.getHoraEntrega();
        dto.horaDevolucao = e.getHoraDevolucao();
        dto.devolvidaPor = e.getDevolvidaPor();
        dto.observacoes = e.getObservacoes();

        if (dto.tipo == TipoChaveEnum.CHAVE) {
            dto.descricao = e.getChave().getCodigoChave();
            dto.sala = e.getChave().getSala() != null
                    ? String.valueOf(e.getChave().getSala().getNumeroSala())
                    : null;
        } else {
            dto.descricao = e.getChave().getCodigoMolho();
        }

        if (e.getFuncionarioComChave() != null) {
            dto.nomePessoa = e.getFuncionarioComChave().getNomeFuncionario();
        } else if (e.getVisitanteComChave() != null) {
            dto.nomePessoa = e.getVisitanteComChave().getNomeVisitante();
        }

        return dto;
    }

    // getters
    public int getIdEntrega() {
        return idEntrega;
    }

    public String getDescricao() {
        return descricao;
    }

    public TipoChaveEnum getTipo() {
        return tipo;
    }

    public String getSala() {
        return sala;
    }

    public String getNomePessoa() {
        return nomePessoa;
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