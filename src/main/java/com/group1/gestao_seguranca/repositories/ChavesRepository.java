package com.group1.gestao_seguranca.repositories;

import com.group1.gestao_seguranca.entity.Chaves;
import com.group1.gestao_seguranca.enums.StatusChaveEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChavesRepository extends JpaRepository<Chaves, Integer> {
// TODO: criar query para buscar View de Status da chave
    // ── Autocomplete para entrega (chaves disponíveis) ───────────────────
    @Query("""
                SELECT c FROM Chaves c
                LEFT JOIN FETCH c.tipoChave
                WHERE c.ativo = :status
                  AND LOWER(c.codigo) LIKE LOWER(CONCAT('%', :q, '%'))
                ORDER BY c.codigo
            """)
    List<Chaves> buscarDisponiveisPorTermo(
            @Param("q") String q,
            @Param("status") StatusChaveEnum status
    );

    // ── Autocomplete para devolução (chaves emprestadas) ─────────────────
    @Query("""
                SELECT c FROM Chaves c
                LEFT JOIN FETCH c.tipoChave
                WHERE c.ativo = true
                AND LOWER(c.codigo) LIKE LOWER(CONCAT('%', :q, '%'))
                ORDER BY c.codigo
            """)
    List<Chaves> buscarEmprestadaPorTermo(
            @Param("q") String q,
            @Param("status") StatusChaveEnum status
    );
}