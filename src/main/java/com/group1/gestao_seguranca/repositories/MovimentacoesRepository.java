package com.group1.gestao_seguranca.repositories;

import com.group1.gestao_seguranca.entities.Movimentacoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovimentacoesRepository extends JpaRepository<Movimentacoes, Integer> {

    @Query("SELECT COUNT(m) > 0 FROM Movimentacoes m WHERE m.funcionario.id = :id AND m.horaSaida IS NULL")
    boolean existeEntradaAtiva(@Param("id") int id);

    @Query("SELECT COUNT(m) > 0 FROM Movimentacoes m WHERE m.visitante.id = :id AND m.horaSaida IS NULL")
    boolean existeEntradaAtivaVisitante(@Param("id") int id);

    @Query("""
            SELECT m FROM Movimentacoes m
            LEFT JOIN FETCH m.funcionario
            LEFT JOIN FETCH m.visitante
            LEFT JOIN FETCH m.funcionarioResponsavel
            WHERE m.horaSaida IS NULL AND m.ativo = true
            ORDER BY m.horaEntrada DESC
            """)
    List<Movimentacoes> findAtivas();

    @Query("""
            SELECT m FROM Movimentacoes m
            LEFT JOIN FETCH m.funcionario
            LEFT JOIN FETCH m.visitante
            LEFT JOIN FETCH m.funcionarioResponsavel
            ORDER BY m.horaEntrada DESC
            """)
    List<Movimentacoes> findAllByOrderByHoraEntradaDesc();

    @Query("""
            SELECT m FROM Movimentacoes m
            LEFT JOIN FETCH m.funcionario
            LEFT JOIN FETCH m.visitante
            LEFT JOIN FETCH m.funcionarioResponsavel
            WHERE m.funcionario.id = :id
            ORDER BY m.horaEntrada DESC
            """)
    List<Movimentacoes> findByFuncionarioIdOrderByHoraEntradaDesc(@Param("id") int id);

    @Query("""
            SELECT m FROM Movimentacoes m
            LEFT JOIN FETCH m.funcionario
            LEFT JOIN FETCH m.visitante
            LEFT JOIN FETCH m.funcionarioResponsavel
            WHERE m.visitante.id = :id
            ORDER BY m.horaEntrada DESC
            """)
    List<Movimentacoes> findByVisitanteIdOrderByHoraEntradaDesc(@Param("id") int id);

    @Query("""
            SELECT m FROM Movimentacoes m
            LEFT JOIN FETCH m.funcionario f
            LEFT JOIN FETCH m.visitante v
            WHERE m.horaSaida IS NULL
              AND (
                LOWER(f.nomeFuncionario) LIKE LOWER(CONCAT('%', :nome, '%'))
                OR LOWER(v.nomeVisitante) LIKE LOWER(CONCAT('%', :nome, '%'))
              )
            ORDER BY m.horaEntrada DESC
            """)
    List<Movimentacoes> buscarAtivasPorNome(@Param("nome") String nome);
}
