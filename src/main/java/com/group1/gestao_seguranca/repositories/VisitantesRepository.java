package com.group1.gestao_seguranca.repositories;

import com.group1.gestao_seguranca.entities.Visitantes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisitantesRepository extends JpaRepository<Visitantes, Integer> {

    // Verifica se já existe um documento de identificação
    boolean existsByDocumentoIdentificacao(String documentoIdentificacao);

    // Busca por documento de identificação
    Optional<Visitantes> findByDocumentoIdentificacao(String documentoIdentificacao);

    // Busca avançada por nome (suporta até 3 palavras/chunks)
    @Query("""
            SELECT v FROM Visitantes v 
            WHERE LOWER(v.nomeVisitante) LIKE LOWER(CONCAT('%', :t1, '%'))
              AND LOWER(v.nomeVisitante) LIKE LOWER(CONCAT('%', :t2, '%'))
              AND LOWER(v.nomeVisitante) LIKE LOWER(CONCAT('%', :t3, '%'))
            ORDER BY v.nomeVisitante ASC
           """)
    List<Visitantes> procurarNomeVisitante(
            @Param("t1") String t1,
            @Param("t2") String t2,
            @Param("t3") String t3
    );

    // ====================== SOFT DELETE ======================

    // Verifica se o visitante existe e está ativo (importante para update e delete)
    boolean existsByIdAndAtivo(Integer id, boolean ativo);

    // Busca apenas visitantes ativos
    List<Visitantes> findAllByAtivoTrue();
}