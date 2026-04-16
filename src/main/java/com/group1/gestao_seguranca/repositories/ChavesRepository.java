package com.group1.gestao_seguranca.repositories;

import com.group1.gestao_seguranca.entities.Chaves;
import com.group1.gestao_seguranca.enums.StatusChaveEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChavesRepository extends JpaRepository<Chaves, Integer> {

    // ── Autocomplete para entrega (chaves disponíveis) ───────────────────
    @Query("""
                SELECT c FROM Chaves c
                WHERE c.status = :status
                  AND (
                    LOWER(c.codigoChave) LIKE LOWER(CONCAT('%', :q, '%'))
                    OR LOWER(c.codigoMolho) LIKE LOWER(CONCAT('%', :q, '%'))
                  )
                ORDER BY c.codigoChave, c.codigoMolho
            """)
    List<Chaves> buscarDisponiveisPorTermo(
            @Param("q") String q,
            @Param("status") StatusChaveEnum status
    );

    // ── Autocomplete para devolução (chaves emprestadas) ─────────────────
    @Query("""
                SELECT c FROM Chaves c
                WHERE c.status = :status
                  AND (
                    LOWER(c.codigoChave) LIKE LOWER(CONCAT('%', :q, '%'))
                    OR LOWER(c.codigoMolho) LIKE LOWER(CONCAT('%', :q, '%'))
                  )
                ORDER BY c.codigoChave, c.codigoMolho
            """)
    List<Chaves> buscarEmprestadaPorTermo(
            @Param("q") String q,
            @Param("status") StatusChaveEnum status
    );
}