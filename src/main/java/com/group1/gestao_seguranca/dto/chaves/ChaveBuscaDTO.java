package com.group1.gestao_seguranca.dto.chaves;

import com.group1.gestao_seguranca.entity.Chaves;
import com.group1.gestao_seguranca.enums.TipoChaveEnum;

public record ChaveBuscaDTO(
        Integer idChave,
        String descricao,   // CHV-101 ou "Molho TI"
        String tipo         // "CHAVE" ou "MOLHO"
) {
    public static ChaveBuscaDTO from(Chaves c) {
        String descricao = c.getTipoChave() == TipoChaveEnum.CHAVE
                ? c.getCodigoChave()
                : c.getCodigoMolho();
        return new ChaveBuscaDTO(c.getId(), descricao, c.getTipoChave().name());
    }
}