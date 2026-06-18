package com.group1.gestao_seguranca.repositories;

import com.group1.gestao_seguranca.entity.Visitantes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisitantesRepository extends JpaRepository<Visitantes, Integer> {

    boolean existsByDocumentoIdentificacao(String documentoIdentificacao);

    Optional<Visitantes> findByDocumentoIdentificacao(String documentoIdentificacao);

    @Query("""
             SELECT v FROM Visitantes v
             WHERE LOWER(v.nome) LIKE LOWER(CONCAT('%', :t1, '%'))
               AND LOWER(v.nome) LIKE LOWER(CONCAT('%', :t2, '%'))
               AND LOWER(v.nome) LIKE LOWER(CONCAT('%', :t3, '%'))
             ORDER BY v.nome ASC
            """)
    List<Visitantes> procurarNomeVisitante(
            @Param("t1") String t1,
            @Param("t2") String t2,
            @Param("t3") String t3
    );

    @Query(value = """
            SELECT * FROM visitantes 
            WHERE (
                nome LIKE '%' + :termo + '%'
                OR SOUNDEX(nome) = SOUNDEX(:termo)
            )
            AND ativo = 1
            """, nativeQuery = true)
    List<Visitantes> procurar(@Param("termo") String termo, Pageable pageable);

    // ====================== SOFT DELETE ======================
    // Verifica se o visitante existe e está ativo (importante para update e delete)
    Optional<Visitantes> findByIdAndAtivo(Integer id);

    // Busca apenas visitantes ativos
    Page<Visitantes> findAllByAtivoTrue(Pageable pageable);
}