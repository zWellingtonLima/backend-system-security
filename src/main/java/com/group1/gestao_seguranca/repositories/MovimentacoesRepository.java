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

    // Todas as movimentações ativas (dentro agora)
    List<Movimentacoes> findByHoraSaidaIsNullOrderByHoraEntradaDesc();

    // Histórico completo
    List<Movimentacoes> findAllByOrderByHoraEntradaDesc();

    // Movimentações de um funcionário específico
    List<Movimentacoes> findByFuncionarioIdOrderByHoraEntradaDesc(int idFuncionario);

    // Movimentações de um visitante específico
    List<Movimentacoes> findByVisitanteIdOrderByHoraEntradaDesc(int idVisitante);

    @Query("""
                SELECT m FROM Movimentacoes m
                LEFT JOIN m.funcionario f
                LEFT JOIN m.visitante v
                WHERE m.horaSaida IS NULL
                  AND (
                    LOWER(f.nomeFuncionario) LIKE LOWER(CONCAT('%', :nome, '%'))
                    OR LOWER(v.nomeVisitante) LIKE LOWER(CONCAT('%', :nome, '%'))
                  )
                ORDER BY m.horaEntrada DESC
            """)
    List<Movimentacoes> buscarAtivasPorNome(@Param("nome") String nome);
}
