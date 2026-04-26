package com.group1.gestao_seguranca.repositories;

import com.group1.gestao_seguranca.entities.EntregaChaves;
import com.group1.gestao_seguranca.entities.Movimentacoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EntregaChavesRepository extends JpaRepository<EntregaChaves, Integer> {

    @Query("""
            SELECT e FROM EntregaChaves e
            JOIN FETCH e.chave c
            LEFT JOIN FETCH c.tipoChave
            LEFT JOIN FETCH c.sala
            LEFT JOIN FETCH e.funcionarioComChave
            LEFT JOIN FETCH e.visitanteComChave
            WHERE e.movimentacao = :mov AND e.horaDevolucao IS NULL
            """)
    List<EntregaChaves> findByMovimentacaoAndHoraDevolucaoIsNull(@Param("mov") Movimentacoes mov);

    @Query("""
            SELECT e FROM EntregaChaves e
            JOIN FETCH e.chave c
            LEFT JOIN FETCH c.tipoChave
            LEFT JOIN FETCH c.sala
            LEFT JOIN FETCH e.funcionarioComChave
            LEFT JOIN FETCH e.visitanteComChave
            WHERE e.horaDevolucao IS NULL
            ORDER BY e.horaEntrega DESC
            """)
    List<EntregaChaves> findByHoraDevolucaoIsNullOrderByHoraEntregaDesc();

    @Query("""
            SELECT e FROM EntregaChaves e
            JOIN FETCH e.chave c
            LEFT JOIN FETCH c.tipoChave
            LEFT JOIN FETCH c.sala
            LEFT JOIN FETCH e.funcionarioComChave
            LEFT JOIN FETCH e.visitanteComChave
            WHERE e.horaDevolucao IS NOT NULL
            ORDER BY e.horaDevolucao DESC
            """)
    List<EntregaChaves> findByHoraDevolucaoIsNotNullOrderByHoraDevolucaoDesc();

    @Query("""
            SELECT e FROM EntregaChaves e
            JOIN FETCH e.chave c
            LEFT JOIN FETCH c.tipoChave
            LEFT JOIN FETCH c.sala
            LEFT JOIN FETCH e.funcionarioComChave
            LEFT JOIN FETCH e.visitanteComChave
            WHERE e.horaDevolucao BETWEEN :inicio AND :fim
            ORDER BY e.horaDevolucao DESC
            """)
    List<EntregaChaves> findByHoraDevolucaoBetweenOrderByHoraDevolucaoDesc(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );

    @Query("""
            SELECT e FROM EntregaChaves e
            JOIN FETCH e.movimentacao
            JOIN FETCH e.chave c
            LEFT JOIN FETCH c.tipoChave
            LEFT JOIN FETCH c.sala
            LEFT JOIN FETCH e.funcionarioComChave
            LEFT JOIN FETCH e.visitanteComChave
            WHERE e.movimentacao.id IN :ids AND e.horaDevolucao IS NULL
            """)
    List<EntregaChaves> findPendentesParaMovimentacoes(@Param("ids") List<Integer> ids);

    boolean existsByMovimentacaoAndHoraDevolucaoIsNull(Movimentacoes movimentacao);
}