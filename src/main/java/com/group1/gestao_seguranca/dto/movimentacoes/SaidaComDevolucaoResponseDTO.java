package com.group1.gestao_seguranca.dto.movimentacoes;

import java.util.List;

/**
 * Resposta do endpoint de saída-com-devolução.
 * Inclui a movimentação atualizada, aviso e lista das chaves devolvidas automaticamente.
 */
public class SaidaComDevolucaoResponseDTO {

    private MovimentacaoResponseDTO movimentacao;
    private String aviso;
    private List<String> chavesDevolvidas; // descrições das chaves/molhos devolvidos

    public SaidaComDevolucaoResponseDTO(MovimentacaoResponseDTO movimentacao,
                                        String aviso,
                                        List<String> chavesDevolvidas) {
        this.movimentacao = movimentacao;
        this.aviso = aviso;
        this.chavesDevolvidas = chavesDevolvidas;
    }

    // Construtor sem aviso (quando não havia chaves pendentes)
    public SaidaComDevolucaoResponseDTO(MovimentacaoResponseDTO movimentacao) {
        this.movimentacao = movimentacao;
        this.aviso = null;
        this.chavesDevolvidas = List.of();
    }

    public MovimentacaoResponseDTO getMovimentacao() {
        return movimentacao;
    }

    public String getAviso() {
        return aviso;
    }

    public List<String> getChavesDevolvidas() {
        return chavesDevolvidas;
    }
}