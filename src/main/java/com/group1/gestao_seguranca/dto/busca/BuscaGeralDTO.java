package com.group1.gestao_seguranca.dto.busca;

import com.group1.gestao_seguranca.entities.Funcionarios;
import com.group1.gestao_seguranca.entities.Visitantes;
import com.group1.gestao_seguranca.enums.TipoEntrada;

public record BuscaGeralDTO(
        Integer id,
        String nome,
        TipoEntrada tipo,        // "VISITANTE" ou "FUNCIONARIO"
        String detalhe // setor ou empresa
) {
    public static BuscaGeralDTO fromVisitante(Visitantes v) {
        return new BuscaGeralDTO(
                v.getId(),
                v.getNomeVisitante(),
                TipoEntrada.VISITANTE,
                v.getEmpresa()
        );
    }

    public static BuscaGeralDTO fromFuncionario(Funcionarios f) {
        return new BuscaGeralDTO(
                f.getId(),
                f.getNomeFuncionario(),
                TipoEntrada.FUNCIONARIO,
                f.getSetor()
        );
    }
}