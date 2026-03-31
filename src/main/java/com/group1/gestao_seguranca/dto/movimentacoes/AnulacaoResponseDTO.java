package com.group1.gestao_seguranca.dto.movimentacoes;

import java.util.List;

/**
 * Resposta do endpoint de anulação (usando soft delete).
 * Inclui a movimentação anulada e, se havia chaves pendentes,
 * um aviso e a lista das que foram devolvidas automaticamente.
 */
public class AnulacaoResponseDTO {

    private MovimentacaoResponseDTO movimentacao;
    private String aviso;
    private List<String> chavesDevolvidas;

    public AnulacaoResponseDTO(MovimentacaoResponseDTO movimentacao) {
        this.movimentacao = movimentacao;
        this.aviso = null;
        this.chavesDevolvidas = List.of();
    }

    public AnulacaoResponseDTO(MovimentacaoResponseDTO movimentacao,
                               String aviso,
                               List<String> chavesDevolvidas) {
        this.movimentacao = movimentacao;
        this.aviso = aviso;
        this.chavesDevolvidas = chavesDevolvidas;
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