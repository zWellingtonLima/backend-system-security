package com.group1.gestao_seguranca.dto.movimentacoes;

import com.group1.gestao_seguranca.entity.Movimentacoes;

public record EntradaAtivaDTO(
        int idMovimentacao,
        String nomePessoa,
        String setorDestino
) {
    public static EntradaAtivaDTO from(Movimentacoes m) {
        String nome = m.getFuncionario() != null
                ? m.getFuncionario().getNome()
                : m.getVisitante().getNomeVisitante();

        return new EntradaAtivaDTO(
                m.getId(),
                nome,
                m.getSetorDestino()
        );
    }
}