package com.group1.gestao_seguranca.dto.chaves;

import com.group1.gestao_seguranca.entities.EntregaChaves;
import com.group1.gestao_seguranca.enums.TipoChaveEnum;

import java.time.LocalDateTime;

public class ChaveEmprestadaDTO {

    private int idEntrega;
    private int idChave;
    private String descricao;       // CHV-101 ou "Molho TI"
    private TipoChaveEnum tipo;
    private String sala;            // número da sala ou null se molho
    private String nomePessoa;      // quem tem a chave
    private LocalDateTime horaEntrega;
    private String observacoes;

    public static ChaveEmprestadaDTO from(EntregaChaves e) {
        ChaveEmprestadaDTO dto = new ChaveEmprestadaDTO();
        dto.idEntrega = e.getId();
        dto.idChave = e.getChave().getId();
        dto.tipo = e.getChave().getTipoChave();
        dto.horaEntrega = e.getHoraEntrega();
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

    public int getIdChave() {
        return idChave;
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

    public String getObservacoes() {
        return observacoes;
    }
}