package com.group1.gestao_seguranca.dto.chaves;

import com.group1.gestao_seguranca.entities.Chaves;
import com.group1.gestao_seguranca.enums.TipoChaveEnum;

public class ChaveAutocompleteDTO {

    private int id;
    private String descricao;   // codigoChave ou codigoMolho
    private TipoChaveEnum tipo;
    private String sala;        // número da sala, ou null se molho
    private String status;

    public static ChaveAutocompleteDTO from(Chaves c) {
        ChaveAutocompleteDTO dto = new ChaveAutocompleteDTO();
        dto.id     = c.getId();
        dto.tipo   = c.getTipoChave();
        dto.status = c.getStatus().name();

        if (c.getTipoChave() == TipoChaveEnum.CHAVE) {
            dto.descricao = c.getCodigoChave();
            dto.sala = c.getSala() != null
                    ? String.valueOf(c.getSala().getNumeroSala())
                    : null;
        } else {
            dto.descricao = c.getCodigoMolho();
        }
        return dto;
    }

    public int getId()           { return id; }
    public String getDescricao() { return descricao; }
    public TipoChaveEnum getTipo() { return tipo; }
    public String getSala()      { return sala; }
    public String getStatus()    { return status; }
}