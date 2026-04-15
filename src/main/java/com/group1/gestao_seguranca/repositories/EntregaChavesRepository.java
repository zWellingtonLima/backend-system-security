package com.group1.gestao_seguranca.repositories;

import com.group1.gestao_seguranca.entities.EntregaChaves;
import com.group1.gestao_seguranca.entities.Movimentacoes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EntregaChavesRepository extends JpaRepository<EntregaChaves, Integer> {

    List<EntregaChaves> findByMovimentacaoAndHoraDevolucaoIsNull(Movimentacoes movimentacoes);

    // Chaves ainda não devolvidas (página de gestão)
    List<EntregaChaves> findByHoraDevolucaoIsNullOrderByHoraEntregaDesc();

    // Histórico completo (apenas devolvidas)
    List<EntregaChaves> findByHoraDevolucaoIsNotNullOrderByHoraDevolucaoDesc();

    // NOVO: Histórico do dia (devolvidas entre início e fim do dia)
    List<EntregaChaves> findByHoraDevolucaoBetweenOrderByHoraDevolucaoDesc(
            LocalDateTime inicio,
            LocalDateTime fim
    );

    boolean existsByMovimentacaoAndHoraDevolucaoIsNull(Movimentacoes movimentacao);
}