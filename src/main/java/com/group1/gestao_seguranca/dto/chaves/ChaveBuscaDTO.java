package com.group1.gestao_seguranca.dto.chaves;

import com.group1.gestao_seguranca.entity.Chaves;
import com.group1.gestao_seguranca.enums.TipoChaveEnum;

public record ChaveBuscaDTO(
        Integer idChave,
        String descricao,   // CHV-101 ou "Molho TI"
        TipoChaveEnum tipo         // "CHAVE" ou "MOLHO"
) {
    public static ChaveBuscaDTO from(Chaves c) {
        TipoChaveEnum tipo = c.getTipoChave();
        return new ChaveBuscaDTO(c.getId(), c.getCodigo(), c.getTipoChave());
    }
}